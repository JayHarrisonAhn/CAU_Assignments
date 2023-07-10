#include "ns3/aodv-module.h"
#include "ns3/dsr-module.h"
#include "ns3/dsdv-module.h"
#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/mobility-module.h"
#include "ns3/wifi-module.h" 
#include "ns3/applications-module.h"
#include "ns3/netanim-module.h"
#include "ns3/flow-monitor-module.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cmath>

using namespace ns3;

char* outputFilename = (char *)"manet";
std::string topology = "./scratch/manet100.csv";
uint32_t size = 10;
double totalTime=50;
double txrange = 50;

int packetsSent = 0;
int packetsReceived = 0;
int bytesSent = 0;
int bytesReceived = 0;

NodeContainer nodes;
NetDeviceContainer devices;
Ipv4InterfaceContainer interfaces;

void CreateNodes ()
{
  std::cout << "Creating " << (unsigned)size << " nodes with transmission range " << txrange << "m.\n";
  nodes.Create (size);
  // Name nodes
  for (uint32_t i = 0; i < size; ++i)
    {
      std::ostringstream os;
      os << "node-" << i;
      Names::Add (os.str (), nodes.Get (i));
    }
  
  Ptr<ListPositionAllocator> positionAllocS = CreateObject<ListPositionAllocator> ();
  
  std::string line;
  std::ifstream file(topology);
  
  uint16_t i = 0																																																																																																																																																																																																																																																																														;
  double vec[3];
  
  if(file.is_open())
  {
	while(getline(file,line))
	{
		
		//std::cout<<line<< '\n';
		char seps[] = ",";
		char *token;

		token = strtok(&line[0], seps);
		
		//std::cout << token << "\n";
		
		while(token != NULL)
		{
			//printf("[%s]\n", token);
			vec[i] = atof(token);
			i++;
			token = strtok (NULL, ",");
			if(i == 3)
			{
				//std::cout << "\n" << vec[0] << "  " << vec[1] << "   " << vec[2] << "\n";
				positionAllocS->Add(Vector(vec[1], vec[2], 0.0));
				i = 0;
			}
        }

	  }
	  file.close();
	}
	else
	{
	  std::cout<<"Error in csv file"<< '\n';
	}

  MobilityHelper mobilityS;
  mobilityS.SetPositionAllocator(positionAllocS);
  mobilityS.SetMobilityModel("ns3::RandomWalk2dMobilityModel", 
                             "Bounds", RectangleValue(Rectangle(0, 100, 0, 100)),
                             "Direction", StringValue("ns3::UniformRandomVariable[Min=0|Max=360]"),
                             "Speed", StringValue("ns3::ConstantRandomVariable[Constant=20]"));
  mobilityS.Install(nodes);
}

void SentPacket (Ptr<Socket> socket, uint32_t dataSent)
{
  packetsSent += 1;
  bytesSent += dataSent;
}

void ReceivePacket (Ptr<Socket> socket)
{
  Ptr<Packet> packet;
  while ((packet = socket->Recv ()))
    {
      packetsReceived++;
      bytesReceived += packet->GetSize();
    }
}

static void GenerateTraffic (Ptr<Socket> socket, uint32_t pktSize, 
                             uint32_t pktCount, Time pktInterval )
{
  if (pktCount > 0)
    {
      socket->Send (Create<Packet> (pktSize));
      Simulator::Schedule (pktInterval, &GenerateTraffic, 
                           socket, pktSize,pktCount-1, pktInterval);
    }
  else
    {
      socket->Close ();
    }
}

int main(int argc, char **argv)
{
  

  int packetSize = 1024;
  int totalPackets = totalTime-1;
  double interval = 1.0; 
  Time interPacketInterval = Seconds (interval);
  
  
  
  CreateNodes ();


  WifiMacHelper wifiMac;
  YansWifiPhyHelper wifiPhy;
  WifiHelper wifi;
  
  wifiMac.SetType ("ns3::AdhocWifiMac");
  YansWifiChannelHelper wifiChannel = YansWifiChannelHelper::Default ();
  wifiPhy.SetChannel (wifiChannel.Create ());
  wifi.SetRemoteStationManager ("ns3::ConstantRateWifiManager", "DataMode", StringValue ("OfdmRate6Mbps"), "RtsCtsThreshold", UintegerValue (0));
  devices = wifi.Install (wifiPhy, wifiMac, nodes); 
  
  InternetStackHelper stack;

  //////////////////// AODV ////////////////////
  AodvHelper aodv;
  stack.SetRoutingHelper (aodv);
  stack.Install (nodes);
  //////////////////////////////////////////////

  //////////////////// DSR  ////////////////////
  // DsrMainHelper dsrMain;
  // DsrHelper dsr;
  // stack.Install (nodes);
  // dsrMain.Install (dsr, nodes);
  //////////////////////////////////////////////

  //////////////////// DSDV ////////////////////
  // DsdvHelper dsdv;
  // stack.SetRoutingHelper (dsdv);
  // stack.Install (nodes);
  //////////////////////////////////////////////

  Ipv4AddressHelper address;
  address.SetBase ("10.0.0.0", "255.0.0.0");
  interfaces = address.Assign (devices);
  
  Ipv4GlobalRoutingHelper::PopulateRoutingTables ();
  
  for(int i = 0; i < (size / 2); i++) 
  {
    TypeId tid = TypeId::LookupByName ("ns3::UdpSocketFactory");
    Ptr<Socket> recvSink = Socket::CreateSocket (nodes.Get (size-1-i), tid);
    InetSocketAddress local = InetSocketAddress (Ipv4Address::GetAny (), 8080);
    recvSink->Bind (local);
    recvSink->SetRecvCallback (MakeCallback (&ReceivePacket));

    Ptr<Socket> source = Socket::CreateSocket (nodes.Get (i), tid);
    InetSocketAddress remote = InetSocketAddress (interfaces.GetAddress (size-1-i,0), 8080);
    source->Connect (remote);
    source->SetDataSentCallback (MakeCallback (&SentPacket));
    // source->SetCallba (MakeCallback (&SentPacket));

    Simulator::Schedule (Seconds (1), &GenerateTraffic, source, packetSize, totalPackets, interPacketInterval);
  }
                       
  std::cout << "Starting simulation for " << totalTime << " s ...\n";
  AnimationInterface anim ("scratch/dsr-output.xml");
  
  wifiPhy.EnablePcapAll (outputFilename);
          
  Simulator::Stop (Seconds (totalTime));
  Simulator::Run ();
  Simulator::Destroy ();
  
  std::cout << "\n\n";
  std::cout << "  Total Tx Packets: " << packetsSent << "\n";
  std::cout << "  Total Rx Packets: " << packetsReceived << "\n";
  std::cout << "  Throughput: " << ((bytesReceived * 8.0) / totalTime)/1024<<" Kbps"<<"\n";
  std::cout << "  Packets Delivery Ratio: " << ((float)packetsReceived/(float)packetsSent)*100.0 << "%" << "\n";
  
}