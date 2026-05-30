# 01. 시스템

## 1. 시스템 기본 학습

- 리눅스 시스템 관련 로그 및 보안 설정 *PAM등
- 리눅스 권한 설정 및 검색 관련 주요 명령어
- 시스템 취약점 분석 및 평가 ( 단순 명령 암기 말고 취약점의 원인과 보안 설정의 의미를 정확히 이해하며 서술)

- 윈도우 인증과정
    - LSA : 감사 로그를 기록
    - SAM : 데이터베이스 파일 C:\Windows,  정보 비교 후 인증 여부 결정
        - Administrators 및 System 그룹 외에는 SAM 파일에 대한 접근을 제한한다.
    - SRM : SID 부여, 파일이나 디렉에 대한 접근을 허용할지 여부 결정
- 윈도우 인증 암호 알고리즘
    - LM 해시
    - NTLM 해시
    - NTMLv2 해시

  → LAN Manager (네트워크를 통한 파일 및 프린터 공유 등과 같은 작업 시 인증을 담당) 인증 수준 설정을 통해 Challenge/response 인증 프로토콜을 NTLMv2 사용을 권장

- 패스워드 크래킹 : 사전 공격, 무차별 공격, 혼합 공격, 레인보우 테이블을 이용한 공격

---

## 2.  UNIX/Linux 기본 학습

### (1) 시스템 기본

- 사용자 정보
    - passwd 파일
        - 사용자 계정 정보를 저장하고 있는 파일로 콜론(:)을 구분자로 7개의 필드
        - /etc/passwd
        - `algisa : x : 519 : 514 : : /home/algisa : /bin/bash`
        - `root : x : 0 : 0 : root : /root : /bin/bash`
        - 사용자 계정명 : 사용자 패스워드 : UID : GID : 설명 : 홈 디렉터리 : 로그인 쉘
            - 로그인 쉘 : 사용자가 로그인에 성공한 후에 동작할 셸 프로그램
        - x의 의미는 shadow 패스워드 정책을 사용한다는 의미
    - 공격자가 root 권한을 탈취하기 위해 공격자가 접근할 수 있는 일반 사용자 계정의 UID를 0으로 조작하는 공격이 발생할 수 있다. 주기적으로 부적절하게 UID가 0으로 설정된 일반 사용자 계정이 점검하는 것이 필요함
    - `id <사용자 계정명>`

      uid=519(algisa) gid=514(dev) groups=514(dev)

- 셸 (shell) : 사용자와 커널(운영체제) 간 인터페이스 역할을 하는 프로그램으로 사용자가 입력한 명령어를 해석하여 커널에 전달하고 그 결과를 출력해 준다. `2025`
- 그룹정보
    - group 파일
        - 그룹 정보를 저장하고 있는 파일로 콜론(:)을 구분자로 4개의 필드
        - /etc/group

        ```bash
        ls -l /etc/group
        cat /etc/group | egrep '^root|^dev'
        
        root : x : 0 : root
        dev : x : 514 : alice,bob,eve
        그룹명/패스워드(사용안함)/GID/소속된 사용자 
        ```

    - 사용자 패스워드 변경

        ```bash
        passwd           # 자신의 패스워드 변경 
        passwd {사용자명}  # 특정 사용자의 패스워드 변경 (루트궘한) 
        ```

- 입출력 재지정 (I/O Redirection) 기능
    - 표준 입력 (STDIN, FD:0)
    - 표준 출력 (STDOUT, FD:1)
    - 표준 에러 (STDERR, FD:2)
    - 명령어

        ```bash
        <command> [0] < file_name
        <command> [1 or 2] > file_name 
        id > id.dat #overwrite 모드, default는 1로 표준출력임
        id 1 >> id.dat #append 모드 
        ls -l a.dat b.dat 1>ls.out 2>&1 #표준출력은 ls.out에, 에러출력은 표준출력와 같은 곳에
        ```

- 파이프 or 파이프라인 기능
    - 명령어의 실행 결과를 다른 명령어의 입력으로 전달하여 처리하도록 하는 기능 `|`

    ```bash
    cat a.log | more # more : 파일 내용을 터미널 화면 크기 단위로 출력
    ps -df | grep httd # grep : 지정한 패턴의 행만 출력 (grep -v 으로 하면 그것만 제외한 행)
    ```

- 특수문자 (p.16 아는 내용임)

### (2) 파일시스템 응용

- 파일시스템 구성
    - 부트 블록 : 운영체제를 부팅하거나 초기화하기 위한 부트스트랩 코드를 담고 있는 블록
    - 슈퍼 블록 : 파일시스템을 관리하기 위한 정보를 담고 있는 블록
    - **아이노드 리스트
        - inode number : 파일시스템 내에서 해당 파일을 식별하기 위한 고유한 식별자
        - 파일 타입 : 일반(정규) 파일, 디렉터리, 장피파일 등
        - 접근권한
        - link count : 해당 inode를 참조하는 링크 개수 (하드 링크 개수)
        - MAC Time
            - Last Modification Time : 파일의 내용을 마지막으로 수정한 시간
            - Last Access Time : 파일을 마지막으로 접근한 시간
            - Last Change Time : 파일의 속성을 마지막으로 변경한 시간
        - **inode레는 파일명이 없으며 파일명은 디렉터리를 통해 관리된다.
        - **파일에 대한 무결성 확인을 위한 타임라인 분석을 수행한다. 이때 inode 구조체의 MAC Time을 점검한다.
        - `stat /etc/passwd`
    - 데이터 블록
- 파일시스템과 링크 파일
    - 하드 링크 : 기존 파일과 동일한 inode number을 가지는 파일. 디렉터리는 하드 링크가 불가능함
        - `In a.dat a_hi.dat`
    - 심볼릭 링크  : 동일 파일시스템 내에서만 링크할 수 있고 디렉터리 링크 불가한 하드 링크 단점 보완, 원본 파일에 대한 파일 경로를 파일 내용으로 하는 새로운 파일 생성
        - `In -s a.dat a_sy.dat`
- 디렉터리 관리
    - 파일의 종류
        - 일반(정규) 파일
        - 디렉터리 : 파일명과 해당 파일의 inode number 정보를 목록으로 가지고 있는 특별한 파일
    - ls 명령어
        - `-l`  자세히 `-a` 숨김 파일 포함 `-R` 하위 디렉터리에 있는 내용까지 `-i`  inode number 정보
    - 접근권한
        - 소유자 변경 : `chown` , 소유그룹 변경 : `chgrp`

            ```bash
            chown root d.txt
            chgrp root d.txt
            ```

        - chmod

            ```bash
            chmod u+x a.txt # User에 x 권한 추가
            chmod go+x a.txt # 그룹, others에 x 권한 추가 
            chmod +x a.txt # all (User, group, others)에 x 권한 추가
            chmod u=rw,g=r,o=r a.txt 
            
            chmod 755 a.txt 
            ```

        - 접근권한 마스크 `umask`

          파일 또는 디렉터리 생성 시 접근권한을 설정하기 위해 사용되는 값으로 제거할 접근권한 정보가 설정되어 있다.

            ```bash
            umask # 현재 mask 설정 확인
            0022 # 파일/디렉터리 생성 시 group, others에 w권한 제거. 022 이상 설정 권고
            
            umask 333 
            ```

    - find 명령어로 취약점 점검 **

        ```bash
        # 현재 디렉터리(.) 이하 모든 일반 파일에 대하여 소유자가 root인 파일 검색 
        find . -type f -user root
        # 옵션 간에 논리 연산을 명시하지 않으면 AND (-a) 연산을 수행, OR(-o / -or) , NOT (! / -not) 명시 필요 
        find . -type f \( -user root -o -group dev \)
        find . -user algisa ! -group dev
        # find 검색 후 -exec 옵션을 통해 추가 명령 실행 ls -al 명령을 수행한 결과 출력 
        # {}은 검색 결과 파일들을 ls -al 명령의 인수로 전달하기 위한
        # \; 은 명령의 끝을 의미
        find . -name "*.dat" -user root -exec ls -al {} \;
        ```

        - 현재 디렉터리 이하 모든 파일에 대하여 소유자가 존재하지 않거나 소유그룹이 존재하지 않는 파일 검색
            - `find . \( -nouser -o -nogroup \) -exec ls -al {} \;`
            - **발생 사유 : 현재 삭제된 사용자 계정 또는 그룹의 소유였거나 관리 소홀로 인해 생선된 파일
        - MAC 타임 관련 검색
            - `find . -type f -mtime -10 -exec ls -al {} \;` : 파일의 마지막 수정일이 10일 미만인 파일
                - `-mtime [+-] n` : 마지막으로 파일 내용이 수정된 시간 일(n) 단위 지정
                - `-atime`
                - `-ctime`
        - 접근 권한 관련 검색
            - `find . -type f -perm 600` : 소유자에 rw(6) 권한이 있고 소유그룹 및 기타 사용자는 권한이 없는 (0) 파일
            - `-perm 600`퍼미션이 **정확히** 600`-perm -600`600에 해당하는 **비트가 모두 포함**되어 있음
            - `find / -type f -perm -2 -exec ls -al {} \;`
                - 루트 디렉 이하 모든 일반 파일에 대하여 소유자나 소유그룹에 속하지 않은 모든 사용자에게 (others) 쓰기 권한이 있는 파일을 검색 . 이런 파일을 *World writable* 이라고 한다.

### (3) 프로세스 응용

- 프로세스가 수행을 종료했지만, 커널(운영체제) 오류 또는 부모 프로세스가 종료한 자식 프로세스에 대한 처리 오류 (종료 상태정보를 처리하지 않음) 등으로 인하여 소멸 하지 않고 남아있는 상태를 좀비 프로세스 라고 한다.  → 시스템 가용성 관련 문제 발생 가능
- 좀비 프로세스는 상태 정보 필드가 `Z` 로 설정되고 명령어 이름 필드에 `<defunct>` 표시가 추가된다.
- 제거 방법

  좀비 프로세스의 부모 프로세스를 종료시키거나 시스템을 재기동하여 커널 정보를 초기화

  부모 프로세스가 종료되면 대리모 프로세스인 init process에 의해 자식 프로세스가 소멸됨

  좀비 프로세스는 kill 시그널을 통해서도 소멸하지 않는다.

    ```bash
    ps -ef | grep defunct | grep -v grep 
    
    ps -el
    # 프로세스 플래그 정보 / 프로세스의 현재 상태 정보 / PID / Parent PID
    # F S UID PID PPID C ... CMD 
    # 0 Z  0  722 721        child.out <defunct> 
    
    ps -ef | grep 721 # 부모 프로세스 검색
    kill -9 721 # 부모 프로세스 강제 종료
    
    ```

- 프로세스 간 통신 (시그널) 명령어 `kill`
    - 다른 통신 방법 : 파이프, 메시지큐, 공유메모리, 세마포어
    - 리눅스 기준 주요 시그널

    ```bash
    kill -l # 시스템에서 지원하는 시그널 목록 확인 
    kill -9 1000 # 9번 시그널 (SIGKILL)을 PID=1000 프로세스에 전달
    # 무시하거나 임의로 처리할 수 없는 시그널(관리 목적의 시그널)
    # SIGKILL [9] | 프로세스 종료
    # SIGSTOP [10] | 프로세스 정지 
    kill -STOP 4500
    kill -KILL 4000 
    ```


## 3. UNIX/Linux 시스템 관리

### (1) 시스템 시작과 종료

- 시스템 런 레벨
    - 관리자가 시스템 관리의 편의성 목적으로 사용하는 것으로 네트워크를 사용하지 못하게 하거나 일반 사용자의 접근을 차단할 수 있다.

  | run lv | name | comments |
      | --- | --- | --- |
  | 0 | Halt | 시스템 중지  |
  | 1 | Single user mode | 단일 사용자 모드, 시스템 관리 목적 |
  | 2 | Multiuser | 네트워크를 사용하지 않는 다중 사용자 모드  |
  | 3* | Full multiuser mode | 네트워크를 지원하는 다중 사용자 모드  |
  | 4 | Unused | 사용안함 |
  | 5* | X11 | X-window (GUI 환경)을 사용하는 다중 사용자 모드  |
  | 6 | reboot  | 시스템 재부팅 |
    - init 프로세스는  `/etc/inittab` 파일에 정의된 런 레벨에 따라 해당 런 레벨 디렉터리에 있는 스크립트를 실행하여 시스템 초기 환경을 구성한다.
    - 런 레벨 디렉터리 : `/etc/rc.d/rc[런 레벨].d` : rc2.d
    - 현재 시스템 런 레벨 확인 : `who -r` , `runlevel`
- 시스템 시작
    - 부팅 과정 : BIOS 과정 → Boot 프로그램 과정 → Kernel 과정 → init 과정
- 시스템 종료 : `shutdown` (안전 종료)

### (2) 사용자 및 그룹 관리

- 사용자 관리 명령어 : `useradd` , `usermod` , `userdel`

```bash
useradd -o -u 600 -g dev -c "test 01" # -o 옵션을 중복 UID 허용
-m -d /home/test01 # 계정의 홈 디렉터리 설정, 계정 생성과 함께 홈 디렉터리를 생성할 경우 -m 추가 
-s bin/bash # 계정의 로그인 셸 설정 
test01 # 계정명 
```

- 그룹 관리 명령어 : `groupadd` , `groupmod` , `groupdel`

```bash
groupadd -g 600 soc 
groupmod -g 601 -n soc2
```

### (3) 파일시스템 관리

- 파일시스템 (디스크) 여유 공간 크기 관리 `df`
    - 시스템에 마운트된 모든 파일시스템(디스크) 여유 공간의 크기를 확인할 때 사용
    - 마운트 : 디스크 상의 파일시스템을 유닉스/리눅스 시스템에서 접근할 수 있도록 특정 디렉터리에 연결하는 과정 `mount/unmount`
    - 파일시스템의 가용 공간이 부족할 경우 시스템 장애 등 시스템 가용성에 문제가 발생할 수 있다.

    ```bash
    df -h /dev/sda2 #/dev/sda2 파일시스템의 여유 공간 크기 출력, 보기 쉬운 단위로 
    df -h / # '/' 디렉처리에 마운트된 파일시스템의 여유 공간
    ```

- 디렉터리(파일)별 파일시스템 사용관 관리 `du`

### (4) 작업 스케줄 관리

- cron 서비스
    - 구성요소 : 작업 등록 파일, crontab 명령, cron 데몬 프로세스
    - `작업 등록 파일 경로 : /etc/crontab`
        - 관리자가 설정하는 작업 등록 파일로 모든 사용자에 대한 작업을 등록할 수 있다.
    - `사용자별 cron 작업 등록 파일 (사용자별 crontab 파일) : /var/spool/cron/<사용자 계정명 파일>`
        - 개별 사용자가 `crontab` 명령을 이용하여 작업 등록가능
    - 파일 형식

        ```bash
        10 23 * * * root /work/batch2.sh 
        #분 시 일 월 요일 계정명 작업*절대경로임에 유의 
        					#0:일요일 ~ 6:토요일 
        					
        # 매일 8,9,10시에 algisa 계정으로 실행
        8-10 * * * algisa /work/batch.sh
        # 매 5분 간격으로 root계정으로 실행
        */5 * * * * root /work/batch.sh 
        ```

        - * 필드에 해당하는 모든 값
        - - 범위의 값
        - , 값 구분 지정
        - / 간격값 지정
    - crontab 명령 사용

        ```bash
        # 현재 사용자의 작업 등록 파일 편집/출력/삭제 
        	# 일반 사용자는 자신의 작업만 등록하거나 삭제할 수 있다. 
        crontab [-e|-l|-r] 
        # Linux - algisa 계정 작업 등록 파일 편집/출력/삭제  
        crontab -u algisa [-e|-l|-r] 
        # Unix 
        crontab [-e|-l|-r] algisa 
        
        # cron을 통해 자동으로 실행되는 작업은 표준출력,에러가 불필요 하므로
        # 출력 재지정을 통해 표준 출력과 에러를 모두 /dev/null 장치파일로 재지정하여 버린다.
        30 23 * * * /work/batch.sh 1>/dev/null 2>&1
        ```


    - crontab 명령 접근제어
        - `cron.allow`, `cron.deny` 설정 파일을 사용하여 crontab 명령을 실행 할 수 있는 사용자를 제한하여 허용하지 않는 사용자가 임의로 crontab 명령을 통한 작업 등록을 못하도록 설정한다.
        - `/etc/` 하위에 있음
        - allow 파일이 우선하여 적용됨
        - 2개의 파일이 모두 없는 경우 대부분 시스템에서 root만 명령 사용 가능

## 4. UNIX/Linux 서버보안

### (1) 시스템 보안

- 사용자의 패스워드 관리
    - passwd 파일 : 사용자 로그인 셸 점검 `/sbin/nologin or /bin/false`
        - 로그인이 불필요한 계정에 대해서는 로그인을 금지하도록 설정
    - shadow 파일 : 패스워드 에이징 정보 확인
      - 
        - `root:$1$1Tyc0bAE$DDFLKSDL2FKL:16118:1:90:7:::`
        - 계정명/패스워드/마지막패스워드변경일/minlife/maxlife/warn(패스워드 만료 이전 경고일수/inactive(패스워드 만료 이후 계정 잠기기 전까지 비활성 일수/expires (계정 만료일 설정)
        - 패스워드 필드는 `$ID$Salt$encrypted_password` 형식으로 구성
            - ID - 1:MD5 | 2:BlowFish | 5:SHA-256 (이거부터 권장) ..
            - 솔트를 통해 레인보우 테이블 공격에 효과적으로 대응할 수 있다.
        - 에이징 정보 관련 `chage` 명령 사용법

            ```bash
            # 리눅스 패스워드 정책 설정 파일 : login.defs
            chage -m 1 sj # mindays
                   -M      # maxdays
                   -I      # inactive 
                   -E      # Exprie day 
            # 한번에 다 설정
            chage -m 1 -M 30 -I 7 -E 2025-12-31 sj
            # 현재 설정 확인
            chage -l sj
            ```

        - 패스워드 필드에 다음과 같은 기호일 때 의미
            - *  : 패스워드 잠긴 상태, 별도 인증 사용하여 로그인 가능
            - !! : 패스워드 잠긴 상태로 로그인 불가, 기본적으로 사용자 계정 생성하고 패스워드 설정하지 않으면 뜸
            - 빈값 : 패스워드 설정 안되어 로그인 가능

    ```bash
    # 사용자 계정 패스워드 저장 정책을 shadow 패스워드 정책으로
    pwconv
    # 일반 패스워드 정책으로 
    pwunconv
    # 패스워드 잠금 설정 명령 (lock)
    passwd -l 계정명  #-> sujeong:!!$1%1Typ...
    # 패스워드 잠금 해제 
    passwd -u 계정명 (unlock)
    ```

- 프로세스 실행권한 [SUID, SGID] p66
    - RUID, EUID

    ```bash
    chmod u+s/g+s a.out 
    chmod 4755/2755 a.out
    # 4 (suid) 2 (sgid) 1 (sticky-bit)
    
    # root 소유 SUID, SGID 실행파일 주기적 검사 
       # 1. find -perm 옵션 이용해 조회 (-4000, -2000)
    find / -user root type -f \( -perm -4000 -o -perm -2000 \) -exec ls -al {} \;
    	 # 2. 권한 제거
    chmod -s [실행파일명] # -s 옵션 넣으면 suid, sgid 다 삭제됨
    
    ```

- 디렉터리 접근권한 [sticky-bit]
    - 공유 폴더같은 곳에서 파일을 자유롭게 파일 생성하되 파일 삭제나 파일명 변경은 소유자만이 가능하도록 하기 위해 사용
    - `chmod 1777 /tmp` : /tmp 공유 디렉터리에 권한 설정
    - Linux : `o+t`  , Unix : `u+t`

### (2) 네트워크 보안

- 보안 셸 SSH
    - 암호화된 원격 터미널/파일 송수신 서비스 제공 `telnet` `ftp`
- 슈퍼 서버 (inetd 데몬)
    - 슈퍼 데몬 방식은 클라이언트 요청은 슈퍼 데몬이 모두 처리하고 개별 서비스를 호출해 주는 방식
    - stand-Alone 방식 : 개별 프로세스별로 서버 프로세스(데몬)이 동작하는 방식으로 속도는 빠르지만 서버 자원을 많이 점유함
    - inetd 데몬은 최초 실행 시 `etc/inetd.conf` 파일의 정보를 참조 하여 서비스할 프로그램들에 대한 정보를 얻는다
- 접근 통제 TCP Wrapper
    - 클라이언트의 IP 주소를 확인하여 서비스 허용
    - `/etc/hosts.allow` `/etc/hosts.deny`   : allow 우선 적용, 두 파일에 다 해당 호스트 정보가 없으면 default는 접근 허용
    - 래퍼 사용할 경우 `inetd.conf` 파일의 실행 경로에는 서비스 경로 대신 `/usr/sbin/tcpd` 를 명시한다.
    - 구조
        - { 서비스명 : ip } `ALL : 192:1.1.1`: 해당 ip 주소에 대해 모든 서비스가 이용 가능하다.
- xinetd 슈퍼데몬
    - TCP Wrapper 기능뿐만이 아니라 자체적으로 다양한 서비스별 접근제어 기능을 제공
    - 설정 파일 형식 (일반 설정)

        ```bash
        # /etc/xinetd.d/telnet
        service telnet {
        	disable = no # 서비스 실행 여부. {no : 실행함, yes : 실행 안함}
        	socket_typw = stream
        	...
        }
        ```

      불필요한 서비스는 실행되지 않도록 `disable = yes` 로 설정하여 비활성화한 후 xinetd 데몬을 재기동한다.

    - 설정 파일 형식 (접근제어 관련 설정) **
        - 공백을 구분자로 사용한다.

        ```bash
        servive telnet {
        	only_from = 192.3.1.0/24 192.3.3.0/24 
        	no_access = 192.3.1.110
        	access_times = 09:00-12:00 13:00-18:00
        	cps = 50 10 # connection per second 초당 최대 연결개수
        	instances = 100 # 동시에 서비스할 수 있는 서버(서비스 프로세스) 개수를 제한하기 위한 최대 서버(서비스 프로세스) 개수 설정
        	per_source = 10 # 출발지 IP 기준으로 서비스 연결 개수를 제한하기 위한 IP별 최대 연결 개수 설정 
        }
        ```

        - cps는  2개의 인수를 지정하는데 첫번째 인수는 초당 최대 연결개수, 두 번째 인수는 초당 최대 연결개수 초과 시 연결을 제한하는 시간(초단위)를 의미한다.

          위 같은 예시 경우 초당 최대 연결개수를 50개로 제한하고 초과 연결 요청 시 10초간 연결을 제한한 후 다시 서비스를 개시한다.


### (3) PAM (장착형 인증 모듈, Pluggable Authentication Modules)

```bash
# 아래 내용들 나오면 단답형으로 PAM 적을 수 있기.

- 리눅스 시스템 내에서 사용되는 각종 애플리케이션 인증을 위해 제공되는 다양한 인증용 라이브러리 
- 일반적으로 /lib/security 디렉에 해당 라이브러리가 저장되어 있다.
- 라이브러리들은 애플리케이션 인증 목적으로 관리자에 의해 선택적으로 사용
- 소프트웨어 개발 시 인증 부분을 독립적으로 개발 가능 
- 각 프로그램 (login, Telnet, FTP 등)은 인증이 필요한 부분에 PAM 라이브러리를 호출한다. 
```

- PAM 설정 파일
    - `/etc/pam.d` : PAM 라이브러리를 이용하는 각 응용 프로그램의 설정 파일이 위치한다. 설정파일명은 응용 프로그램명과 동일하다. `/etc/pam.d/remote`

    ```bash
    #type    #control.  #module-path
    auth     required   pam_securetty.so 
    account  include    system.auth
    ```

    - type : PAM 모듈 종류 ***
        - `account(계정)` : 서비스 사용자 계정의 유효성을 검증하는 유형으로 계정의 유효기간(비밀번호 만료), 접속 가능 시간, 서비스 접근허용 여부 등이 있다.
        - `auth(인증)` : 서비스 사용자 계정의 패스워드 검증, 다른 인증 모듈과의 연동 등 사용자 신원확인을 수행하는 유형으로 패스워드 인증, OTP/보안카드를 통한 인증 등이 있다.
        - `password(패스워드)` : 서비스 사용자 계정의 비밀번호 설정 및 변경 조건을 지정하는 유형으로 패스워드 설정/변경 시 최소길이, 복잡도 설정 등이 있다.
        - `session(세션)` : 서비스 사용자 계정의 인증 처리 전후에 수행할 작업을 지정하는 유형으로 사용자 홈 디렉터리 마운트, 메일함 생성 등이 있다.
    - control : 각 모듈 실행 후 성공 또는 실패에 따른 PAM 라이브러리 행동 결정
        - requisite : 인증에 실패할 경우 즉시 인증을 거부
        - required : 인증에 실패하더라도 다음 라인의 모듈을 실행하지만 최종 결과는 실패
        - sufficient : 이전에 요청된 모듈이 실패하더라도 여기서 성공하면 PAM은 인증 성공 (단, 이전에 있는 required 모듈이 모두 성공일 경우)
    - module-path : PAM에서 사용할 실제 모듈 파일이 위치한 경로. 모듈 이름 (*.so 파일)만 명시할 경우 기본 PAM 모듈 디렉터리 `/lib/security`에서 해당 모듈을 찾는다.
- PAM 활용 예1 : root 계정의 원격 접속 제한 *****
    - root 계정은 시스템을 관리하는 매우 중요한 계정으로 직접 로그인이 가능하면 불법적인 침입자의 목표가 될 수 있다. 따라서 root 계정의 원격 접속을 금지한다.
    - 터미널 접속 시 `/etc/securetty` 파일에 등록되어있는 터미널이 아니면 root 접속을 허용하지 않도록 PAM 설정을 한다.
    - `/etc/securetty`  : pam_securetty.so 모듈이 사용하는 파일로 터미널 접속 시 root 접근 제한 설정 파일이다.
    - 설정 방법
        - `/etc/pam.d` 디렉터리에 있는 remote 서비스 설정 파일에 아래와 같이 pam_securetty.so 모듈을 추가한다.
        - `auth      required       pam_securetty.so`
        - `/etc/securetty` 파일에 `pts/~`  터미널을 모두 제거한다.

            ```bash
            tty11 # tty : console 개념
            #pts/0 # pseudo-terminal 가상 터미널 
            #pts/1
            ```

    - 유닉스/리눅스 시스템별 root 계정 원격 접속 제한 설정 ***


        | OS | 점검 파일 위치 및 점검방법 |
        | --- | --- |
        | SOLARIS | # cat /etc/default/login
        CONSOLE=/dev/console |
        | LINUX | #cat /etc/pam.d/login 
        auth required /lib/security/pam_securetty.so
        # cat /etc/securetty
        pts/0 ~ pts/x 관련 설정이 존재하지 않음 |
        | AIX | #cat /etc/security/user
        rlogin = false |
        | HP-UX | #cat /etc/securetty
        console |
    - SSH root 원격 접속 제한 설정
        - `/etc/ssh/sshd_config` ***
            
            ```bash
            PermitRootLogin no 
            ```
            
        - `service sshd restrat`
- PAM 활용 예2 : 계정 잠금 임계값 설정
    - `/etc/pam.d` 디렉터리에 있는 system-auth 서비스 설정파일에  pam_tally2 모듈 추가

        ```bash
        auth    required   pam_tally2.so  deny=5 unlock_time=120
        ```

      deny=5 : 5회 입력 실패 시 패스워드를 잠근다.

      unlock_time=120 : 계정 잠김 후 마지막 계정 실패 시간부터 설정된 시간이 지나면 자동 계정 잠금 해제 (단위:초)

    - 유닉스/리눅스 시스템별 root 계정 원격 접속 제한 설정 ***


        | OS | 점검 파일 위치 및 점검방법 |
        | --- | --- |
        | SOLARIS | # cat /etc/default/login
        RETRIES=5 |
        | LINUX | #cat /etc/pam.d/system-auth
        auth    required   pam_tally2.so  deny=5 unlock_time=120 no_magic_root 
        /lib/security/pam_securetty.so
        
        account  required /lib/security/pam_tally.so no_magic_root reset |
        | AIX | #cat /etc/security/user
        loginretries=5 |
        
        no_magic_root : root 계정은 패스워드 잠금 설정을 적용하지 않는다.
        
        reset : 접속 시도 성공 시 실패한 횟수 초기화 

- PAM 활용 예3 : root 계정 su 제한
    - su 명령어 사용이 허용된 사용자만 root 계정으로 접속할 수 있도록 설정한다.
    - `pam_wheel.so` 모듈 이용

    ```bash
    # 1.wheel 그룹 (su 명령어 사용그룹)에 su 명령어를 사용할 사용자 추가
    cat /etc/group | grep 'wheel' 
    wheel:x:10:root
    
    usermod -G wheel tmp # -G는 보조그룹 지정 옵션 
    wheel:x:10:root,tmp 
    
    # 2.wheel 그룹의 사용자만 su 명령어를 허용하도록 /etc/pam.d 디렉의 su 서비스 설정 파일에 아래와 같이 설정
    auth   required    pam_wheel.so   use_uid_debug
    
    ```

    - sudo 명령을 이용한 관리자 권한 부여
        - sudo 명령은 다른 사용자 계정 (root포함) 권한으로 명령어를 실행하고자 할 때 사용하는 명령어이다.
        - 관리자로 로그인하는 것을 차단하고 권한이 필요한 경우에만 sudo 명령을 사용하여 제한적으로 관리자 권한의 명령어를 실행하는 것을 보안 관점에서 권장
        - sudo 명령 설정 파일 : `sudoers(/etc/sudoers)`
            - `계정명  호스트명=(실행 권한 계정명) 명령어`
                - 계정명 : 그룹명을 줄 경우 %그룹명, 모두에게 줄거면 ALL
                - 실행 권한 계정명 : 생략시 root
            - `algisa   ALL=(ALL)  NOPASSWD: ALL`

              *sudores 파일에 algisa 계정 등록, algisa 계정은 sudo 명령을 통해 모든 호스트에서 root 권한으로 모든 명령을 비밀번호 확인 없이 실행할 수 있다.*

            - `algisa 192.168.56.100=(ALL) ALL`

              algisa 계정은 sudo 명령을 통해 192. 호스트에서 root 권한으로 모든 명령을 실행할 수 있다.

            - `algisa ALL=(ALL) /was/batch/log_batch.sh`

              algisa 계정은 sudo 명령을 통해 모든 호스트에서 root 권한으로 `/was/batch/log_batch.sh 명령 실행가능`

            - `algisa ALL=(root,algisa) ALL`
        - sudo    [-u 실행 권한 계정명]    명령어

            ```bash
            sudo /batch/log_batch.sh
            sudo -u algisa /batch/log_batch.sh
            ```


### (4) 시스템로그

- 각 로그별 내용, 내용을 보기 위한 명령어 위주로 공부
- utmp(x) : utmp —— upgrade —→ utmpx
    - 현재 로그인한 사용자의 상태정보를 담고 있는 로그 파일
    - `w` `who` `finger`
- wtmp(x)
    - 사용자의 성공한 로그인/로그아웃 정보
    - 시스템의 Boot/Shutdown 정보 : 계정명을 `reboot` 로 조회
    - `last` , `last 계정명`
- lastlog 로그 파일
    - 가장 최근에 성공한 로그인 기록
    - `lastlog` `finger`
    - `lastlog -u 계정명` `lastlog -t 일수`
- btmp (Linix), loginlog (Unix)
    - 실패한 로그인 시도 기록
    - `lastb` , loginlog는 텍스트 파일
- sulog 로그 파일 (Unix)
    - 텍스트 파일
    - `SU 02/28 00:48 + pts/4 algisa1-algisa2`
        - +, - 기호는 명령의 성공 여부
        - algisa1이 algisa2로 su 시도함
- acct/pacct 로그 파일
    - 사용자가 로그아웃할 때까지 입력한 명령어와 터미널의 종류, 프로세스 시작 시간 등을 저장
    - `lastcomm`
    - 명령을 쓰기 전에 `/var/account/pacct` 명령어로 활성화 필요
- messages 로그 파일
    - 가장 기본적인 시스템 로그 파일로 snort 실행 상태에 관한 내용과 TCP Wrapper에 의해 접근 차단된 내용 및 telnet을 통한 원격 접속정보 등을 볼 수 있다.
- xferlog 로그 파일
    - 리눅스에서 FTP 서비스 로그 파일
    - `Set Nov 5 02:29:26 2022 / 2 / 192.168.1.1 / 34567 / /home/user/testfile.html / b / T i g algisa ftp 1 * i`
        - 전송 날짜 및 시간
        - 전송 소요 시간 (초) : 2초
        - 원격 호스트 주소
        - 전송 파일 크기
        - 전송 파일명
        - 전송 유형
            - a : 아스키/텍스트 , b : 바이너리
        - 액션 플래그
            - _ : 액션 없음 , C: 파일이 압축됨, U: 압축된 파일이 해제됨, T : 파일이 아카이브됨
        - 전송방향 (서버 기준으로 생각)
            - i : 서버로 파일 업로드, o: 서버로부터 파일 다운로드. d : 서버에 있는 파일 삭제
        - 접근 방식
            - r : 로컬 시스템 계정, a : 익명 계정, g : 게스트 계정으로 접근
        - 사용자명
        - (마지막에) 완료 상태 **
            - c : 전송 성공, i : 전송 실패
- syslog 설정 및 관리
    - syslog.d (로그 데몬) ←— syslog.conf (설정 파일)
    - `facility.priority        action`
    - priorty : 로그 수준 (Level)
        - emergency `emerg`: 시스템 전면 중단
        - alert : 즉각 조치 필요
        - Critical `crit`: 심각한 오류
        - Error `err`: 일반적인 에러
        - warning : 경고 메시지
        - notice : 에러/오류는 아닌데 관리자 조치 필요
        - information `info`: 의미 있는 정보 메시지
        - debug
        - 로그 수준을 `*`로 지정하면 모든 로그 수준, `none`이면 어떠한 경우에도 로그 노노
    - action : 로그를 어디에 남길 것인지
        - 로그파일 : 파일명 지정
        - 콘솔 : `/dev/console`
        - 원격 로그 서버 : `@192.1.1.1`
        - `user` : 지정된 사용자의 스크린으로 메시지
        - `*` : 현재 로그인 된 모든 사용자의 스크린에 메세지

    ```bash
    kern.* /dev/console # kernel에 관련된 로그를 콘솔에 출력
    # 모든 서비스에 대한 info 수준 이상의 로그를 남기되 none으로 설정한 서비스에 대해서는 기록 노노 
    *.info;mail.none;authpriv.none;cron.none /var/log/messages
    ```

  설정 파일 구문 보고 무슨 뜻인지 서술 가능할 것

- 리눅스 로그 관리  - 로그 파일 순환(rotate)
    - logrotate는 리눅스가 설치되면서 기본 설치되는 로그 파일 관리하기 위한 도구

  | 구분 | 위치 및 실행방법 | 설명 |
      | --- | --- | --- |
  | 데몬(위치) | /usr/sbin/logrotate |  |
  | 데몬 설정 파일  | /etc/logrotate.conf |  |
  | 설정 디렉터리 | /etc/logrotate.d | logrotate를 적용할 프로세스의 개별 설정 파일이 위치한 디렉터리 |
  | cron | /etc/cron.daily/logrotate | cron 데몬에 의해 일 단위로 싱행됨 |
    - logrotate.conf 주요 옵션
        - daily, monthly, weekly
        - rotate n : 순환 로그파일의 개수를 n개로 설정
        - create [퍼미션] [소유자] [소유그룹] : 순환 시 새롭게 로그파일을 생성
        - deteext ** : 로그파일의 확장자로 날짜를 붙여서 보관
        - compress, nocompress
        - size n : 지정한 크기가 되면 로그파일 순환

## 5. 시스템해킹

### 버퍼 오버플로우 공격

- 연속된 메모리 공간을 사용하는 프로그램에서 할당된 메모리의 범위를 넘어선 위치에 자료를 읽거나 쓰려고 할 때 발생한다
- C언어 함수
    - strcpy(char *dst, const chsar *src) : src 문자열을 dst 버퍼에 저장한다.