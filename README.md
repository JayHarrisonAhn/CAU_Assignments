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
#### 20230616
- Supervised : TBA
- Semi-Supervised : TBA
#### 20230615
- Supervised : 74.12%
- Semi-Supervised : 87.2%