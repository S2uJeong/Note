# 개요
회사 시스템은 spring, hibernate, maven, jsp를 이용해서 구축하고 있다.
이에 따라 해당 스펙으로 프로젝트를 만들어보며 구조를 파악해본다.

- project git : https://github.com/S2uJeong/spring-jsp-project

## 빌드
### 빌드 관련 설정파일
- web.xml : 서블릿 매핑
- servlet-context.xml : 스프링 설정
  - viewRisolver prefix, suffix 설정
- pom.xml : 메이븐을 다루는 파일
- [참고](https://sschoi1994.tistory.com/351)