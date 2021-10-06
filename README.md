# Mini-Tower-defense-game

JAVA, by using BFS algorithm it can simulate the best way of mini-tower defense game

### 본 프로젝트의 주요 쟁점

맵에 랜덤으로 타워가 배치되어진 상황이 주어졌을때,  
어떻게 몹들이 이동을 하면 타워의 공격에 피해서 목적지까지 가장 빠르고 가장 많이 살아남을 수 있을지에 대해 구현하는 것 입니다.

몹들이 주어졌을 때 맵에서 타워의 공격을 받을 수 있는 곳을 2차원 배열을 통해 확인하고  
이후 BFS 알고리즘을 적용하여 몹들이 서로 겹치지 않으면서도 타워의 공격을 받지않는 최단 경로를 찾아 이동하도록 구현하였습니다.

세부 구현은 `GroundMob.java`파일을 참고해주세요.

### 시뮬레이션 방법

- target 폴더로 이동을합니다.
- 이후 터미널 창에 `-jar problem2-1.0-SNAPSHOT-shaded.jar`를 입력하면 시뮬레이션이 실행이 됩니다.

<p align="center">
      <img src="https://user-images.githubusercontent.com/80669616/136223865-4f53fc00-a0a2-4d06-a6d6-6c60051579a1.png" width="300"><br>MINI_TOWER_DEFNSE 플레이 화면
</p>

> 본 프로젝트는 POSTECH CSED332 Software Design 에서 진행한 프로젝트의 일부분입니다.
