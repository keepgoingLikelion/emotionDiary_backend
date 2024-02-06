# emotionDiary_backend

- 한 기능 작업할 때마다 이슈 작성해주세요!
- 커밋에 이슈를 매핑하기 위해 커밋이름에 #이슈번호를 적어주세요

## git convention

### commit message

|tag|설명|
|---|---|
|Feat|새 기능 구현|
|Fix|버그 수정|
|Test|테스트 코드|
|Style|기능에 영향을 주지 않는 수정|

- `[tag] #이슈 - 커밋메세지`

### Branch

- main branch
  - main branch로 push시 PR이 요구된다.

- develop branch
  - develop branch로 push시 PR이 요구된다.
  - Approve는 2명 이상이 요구된다.
  - 기능 구현 시 `feature/issueNumber-featureName`으로 분기한다.
  - 오류 해결 시 `bugfix/issueNumber-bugName`으로 분기한다.
  