# CAU Machine Learning Spring 2023

## Installation

```bash
conda env create -n jayharrisonahn python=3.10
```

```bash
conda install jupyter notebook scikit-learn pandas matplotlib seaborn
conda install pytorch::pytorch torchvision torchaudio -c pytorch
```

## Final_challenge
### Execution
```bash
pm2 start .\Supervised_Learning.py --interpreter python --no-autorestart
pm2 start .\Semi-Supervised_Learning.py --interpreter python --no-autorestart
```

### Submit Log
#### 20230613_1630
- Result : TBA
- Log(Supervised)
```
0th performance, res : 61.16
1th performance, res : 67.48
2th performance, res : 69.48
3th performance, res : 70.16
4th performance, res : 71.52
```
- Log(Semi-Supervised)
```
[epoch loss=5.54]
[0th epoch, model_1] ACC : 69.8
[0th epoch, model_2] ACC : 43.4
[epoch loss=3.88]
[1th epoch, model_1] ACC : 73.4
[1th epoch, model_2] ACC : 56.6
[epoch loss=3.33]
[2th epoch, model_1] ACC : 77.2
[2th epoch, model_2] ACC : 65.4
[epoch loss=3.03]
[3th epoch, model_1] ACC : 79.8
[3th epoch, model_2] ACC : 73.0
[epoch loss=2.87]
[4th epoch, model_1] ACC : 81.2
[4th epoch, model_2] ACC : 66.2
[epoch loss=2.68]
[5th epoch, model_1] ACC : 82.8
[5th epoch, model_2] ACC : 73.8
[epoch loss=2.61]
[6th epoch, model_1] ACC : 83.4
[6th epoch, model_2] ACC : 72.0
[epoch loss=2.47]
[7th epoch, model_1] ACC : 83.6
[7th epoch, model_2] ACC : 75.6
[epoch loss=2.41]
[8th epoch, model_1] ACC : 85.2
[8th epoch, model_2] ACC : 76.4
[epoch loss=2.34]
[9th epoch, model_1] ACC : 83.8
[9th epoch, model_2] ACC : 76.0
[epoch loss=2.26]
[10th epoch, model_1] ACC : 84.8
[10th epoch, model_2] ACC : 74.6
[epoch loss=2.22]
[11th epoch, model_1] ACC : 84.6
[11th epoch, model_2] ACC : 79.8
[epoch loss=2.20]
[12th epoch, model_1] ACC : 85.2
[12th epoch, model_2] ACC : 75.4
[epoch loss=2.15]
[13th epoch, model_1] ACC : 84.8
[13th epoch, model_2] ACC : 80.8
[epoch loss=2.10]
[14th epoch, model_1] ACC : 86.2
[14th epoch, model_2] ACC : 77.8

```