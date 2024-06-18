- 터미널과 aws에 띄운 linuxServer 연결하는 명령어 
  - chmod 400 linuxServerKey.pem
  - ssh -i "linuxServerKey.pem" {ec2-user}@ec2-43-202-47-62.ap-northeast-2.compute.amazonaws.com

## 명령어 정리 (상위 커맨드 별)
docker {상위 command} {하위 command} {option} {대상} {인자} 
### 컨테이너 container
| 하위 커맨드 | 내용                                                         | 주요 옵션                |
| ----------- | ------------------------------------------------------------ | ------------------------ |
| start       | 컨테이너 실행                                                | -i                       |
| stop        | 컨테이너 정지                                                |                          |
| create      | 도커 이미지로부터 컨테이너 생성                              | --name -e -p -v          |
| run         | 도커 이미지 내려 받고, 컨테이너 생성, 실행<br /> `docker image pull`, `docker container create`, `docker comtainer start` 가 합쳐진 명령어 | --name -e -p -v -d -i -t |
| rm          | 정지 상태의 컨테이너 삭제                                    | -f -v                    |
| exec        | 실행 중인 컨테이너 속에서 프로그램을 실행                    | -i -t                    |
| ls          | 컨테이너 목록 출력                                           | -a                       |
| cp          | 도커 컨테이너와 도커 호스트 간에 파일을 복사                 |                          |
| commit      | 도커 컨테이너를 이미지로 변환                                |                          |

### 이미지 image
| 하위 커맨드 | 내용                                            | 주요 옵션 |
| ----------- | ----------------------------------------------- | --------- |
| pull        | 도커 허브 등의 리포지토리에서 이미지를 내려받음 |           |
| rm          | 도커 이미지 삭제                                |           |
| Is          | 내려 받은 이미지 목록을 출력                    |           |
| build       | 도커 이미지 생성                                | -t        |

### 볼륨 volume
| 하위 커맨드 | 내용                                  | 주요 옵션 |
| ----------- | ------------------------------------- | --------- |
| create      | 볼륨을 생성                           | --name    |
| inspect     | 볼륨의 상세 정보를 출력               |           |
| ls          | 볼륨의 목록을 출력                    | -a        |
| prune       | 현재 마운트되지 않은 볼륨을 모두 삭제 |           |
| rm          | 지정한 볼륨을 삭제                    |           |

### 네트워크 network
- 도커 네트워크 : 도커 요소 간의 통신에 사용하는 가상 네트워크 

| 하위 커맨드 | 내용                                               | 주요 옵션 |
| ----------- | -------------------------------------------------- | --------- |
| connect     | 컨테이너를 도커 네트워크에 연결                    |           |
| disconnect  | 컨테이너의 도커 네트워크를 연결을 해제             |           |
| create      | 도커 네트워크 생성                                 |           |
| inspect     | 도커 네트워크 상세 정보 출력                       |           |
| ls          | 도커 네트워크의 목록을 출력                        |           |
| prune       | 현재 컨테이너가 접속하지 않은 네트워크를 모두 삭제 |           |
| rm          | 지정한 네트워크를 삭제                             |           |

### 단독 
- 주로 도커 허브의 검색이나 로그인에 사용 

---
## 컨테이너의 통신
- 아파치가 동작 중인 서버에 파일을 두면 이 파일을 웹 사이트 형태로 볼 수 있다.
- 컨테이너를 실행 중인 물리적 컴퓨터가 외부의 접근을 대신 받아 컨테이너에 전달하여 통신한다
  - `-p 8080:80`
  - 컴퓨터 (호스트)의 8080 포트를 컨테이너(아파치)의 80 포트와 연결한다.
  - 같은 웹 서버(컨테이너)를 함께 실행하는 경우, 호스트 번호를 모두 같은 것으로 사용하면 어떤 컨테이너로 가야 하는지 모르기 때문에 포트 번호를 겹치지 않게 연결해야 한다.
  - 만약 같은 포트로 해야 한다면, 리버스 프록시로 서버 이름을 통해 구별하도록 구성한다. 

## 볼륨 마운트
- 컨테이너의 일부를 호스트 컴퓨터의 일부와 같이 다룰 수 있는 기능 
  - 볼륨 : 스토리지의 한 영역을 분할한 것
  - 마운트 : 연결하다, 대상을 연결해 운영체제 또는 소프트웨어의 관리하에 두는 일
- 데이터 퍼시스턴시 : 컨테이너는 생성 및 폐기가 빈번하므로 매번 데이터를 옮기는 대신 처음부터 컨테이너 외부에 둔 데이터에 접근해 사용하는 것이 일반적이다. 이때 데이터를 두는 장소가 마운트된 스토리지 영역이다.
- 스토리지 마운트 종류 : 볼륨 마운트, 바인드 마운트
  - 볼륨 마운트 
    - 도커 엔진이 관리하는 영역 내에 만들어진 볼륨을 컨테이너에 디스크 형태로 마운트 
    - 이름으로 관리가 가능하여 다루기 쉽지만 볼륨에 비해 직접 조작하기 어려워
    - 임시 목적이나 자주 쓰지는 않지만 지우면 안 되는 파일을 두는 목적으로 많이 사용한다.
  - 바인드 마운트
    - 도커가 설치된 컴퓨터의 문서 폴더 또는 바탕화면 폴더 등 도커 엔진에서 관리하지 않는 영역의 기존 디렉터리를 컨테이너에 마운트하는 방식 
    - 자주 사용하는 파일을 두는데 사용한다.
- 구현
  1. 볼륨 생성 : `docker volume create {name}`
  2. 스토리지 마운트 : `docker {컨테이너 생성 명령} -v {볼륨 이름}:{컨테이너 마운트 경로}`
  3. 볼륨 상세 정보, 컨테이너에 마운트 됐는지 확인 : `docker volume inspect {볼륨 이름}`

## 컨테이너로 이미지 생성
- 방법 2가지 
  - commit 커맨드 : 기존에 만들어 둔 컨테이너가 있을 때 사용하면 편리 
    - `docker commit {컨테이너 이름} {새로운 이미지 이름}`
  - Dockerfile 스크립트 : 실제 컨테이너 만들 필요 없음
    - `docker build -t {생성할 이미지 이름} {스크립트 경로}`
- 이미지 옮기는 방법
  - 컨테이너는 먼저 이미지로 변환하지 않으면 옮기거나 복사할 수 없음
  - 이미지도 이미지 상태 그대로는 옮기거나 복사할 수 없다.
  - 도커 레지스트리를 이용하거나
  - `save` 커맨드를 통해 `tar`포맷으로 도커 엔진의 관리 영역 바깥으로 내보내야 한다.
    - `docker save -o {파일 이름.tar} {이미지 이름}`
  - 파일은 호스트 컴퓨터의 파일 시스템에 생성된다.
    - 파일을 다시 도커 엔진에 가져오려면 `load` 커맨드를 사용한다.

### Dockerfile 명령어
| 명령       | 내용                                                         |
| ---------- | ------------------------------------------------------------ |
| from       | 토대가 되는 이미지를 지정                                    |
| add        | 이미지에 파일이나 폴더를 추가                                |
| copy       | 이미지에 파일이나 폴더를 추가                                |
| run        | 이미지를 빌드할 때 실행할 명령어를 지정                      |
| cmd        | 컨테이너를 실행할 때 실행할 명령어 지정                      |
| entrypoint | 컨테이너를 실행할 때 실행할 명령어 강제 지정                 |
| onbuild    | 이 이미지를 기반으로 다른 이미지를 빌드할 때 실행할 명령어를 지정 |
| expose     | 이미지가 통신에 사용할 포트를 명시적으로 지정                |
| volume     | 퍼시스턴시 데이터를 저장한 경로를 명시적으로 지정            |
| env        | 환경변수를 정의                                              |
| workdir    | 명령어를 실행하는 작업 디렉터리 지정                         |
| shell      | 빌드 시 사용할 셰을 변경                                     |
| label      | 이름이나 버전, 저작자 정보를 설정                            |

### 참고 사이트
- aws : https://bcp0109.tistory.com/356
- docker 명령어 : https://docs.docker.com/reference/