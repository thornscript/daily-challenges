## ⚠️ 챌린지: Root?! 누구의 책임인가 - 1
### 문제 내용
최근에 HackerNews를 둘러보다가 컨테이너 보안 스캐닝을 위한 엄청 멋진 도구를 발견했습니다.

조금 시간이 걸려 컨테이너 레지스트리에 연결한 후, 도구가 **99개의 오류**를 감지했습니다! ⚠️

첫 번째 오류를 조사해 보니, **어딘가의 클라우드에서 root 권한으로 실행 중인 컨테이너**가 있었습니다. 이 문제는 간단히 root 권한을 제거하면 해결될 것 같습니다.

그런데, **컨테이너 이름만으로는 누구에게 연락해야 하는지 전혀 알 수 없습니다...**

혹시 이 컨테이너를 소유한 **팀을 알아낼 다른 방법**이 있을까요?

```bash
# 도커 이미지
docker pull fixtheops/someone-to-blame-1:latest
# 혹은 Podman 사용 가능
podman pull fixtheops/someone-to-blame-1:latest
```

### 문제 해결
`inspect`로 이미지를 조사하면 레이블을 통해서 컨테이너를 소유한 팀을 바로 확인할 수 있다. `org.label-schema.team`을 보면 `frontend-team`을 볼 수 있다.

```bash
➜ docker inspect fixtheops/someone-to-blame-1
[
    {
	    # ...
            "OnBuild": null,
            "Labels": {
                "maintainer": "fixtheops",
                "org.label-schema.build-date": "",
                "org.label-schema.description": "A super base docker image to run nodejs",
                "org.label-schema.name": "fixtheops/someonetoblame1",
                "org.label-schema.schema-version": "1.0",
                "org.label-schema.team": "frontend-team",
                "org.label-schema.url": "http://fixtheops.dev"
            }
        },
```
