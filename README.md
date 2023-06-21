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
pm2 start .\Supervised_Learning.py --interpreter pythonw --no-autorestart
pm2 start .\Semi-Supervised_Learning.py --interpreter pythonw --no-autorestart
```

### Submit Log
#### Final
- Supervised : 79.44% (1st in class)
- Semi-Supervised : 90.4% (2nd in class)
#### 20230615
- Supervised : 74.12%
- Semi-Supervised : 87.2%