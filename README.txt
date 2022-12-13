1. Android Studio를 실행합니다.

2. Work Space 폴더에 압축 해제한 motibook 폴더를 추가합니다.
	-> 기본값 C:\Users\[UserName]\AndroidStudioProjects

3. Android Studio를 실행해 motibook Project를 엽니다.

4. 잠시 SDK, Gradle 및 필요한 환경이 갖춰지는 것을 기다립니다. 
	-> AndroidStudio에서 자동으로 설치해줍니다.

5. Android Virtual Device를 준비합니다.
-> 5-1. Android Studio 상단의 Tools -> Device Manager -> Create Device를 선택합니다.
-> 5-2. Pixel 2 검색한 후 Pixel 2 모델을 선택, Next를 누릅니다.
-> 5-3. x86 Images 탭을 누르고 Release Name : S / API Level : 31 / ABI : x86_64 / Target : Android 12.0 (Google APIs) 모델을 선택합니다.
		(Release Name S 옆에 다운로드 아이콘 눌러 설치 가능)
-> 5-4. 기본 설정으로 Finish를 누릅니다.

(다른 Android Virtual Device 사용시 기본 시간설정값에 따라 Google Calendar 일정 시간이 틀어질 수 있습니다.)

6. Android Studio 상단의 Run -> Run 'App'을 눌러 Android Virtual Machine이 잘 동작하는지 확인합니다.

7. 앱이 실행되는 것을 확인했다면, 다시 상단의 Run -> Stop 'App' 을 눌러 종료합니다.

8. 기기 인증하기. ( 이 단계를 진행하지 않으면, 앱의 로그인 기능 및 구글 캘린더, 행사 조회 기능이 정상적으로 동작하지 않습니다.)
-> 8-1. 브라우저에서 https://console.cloud.google.com/apis/credentials?project=motibook 웹 페이지로 이동합니다.
-> 8-2. 페이지 상단의 [+ 사용자 인증 정보 만들기] -> [OAuth 클라이언트 ID]를 선택합니다.
-> 8-3. 애플리케이션 유형 : Android 를 선택합니다.
-> 8-4. 이름 : 기기의 이름입니다. (ex. Motibook Test PC)
		   패키지 이름 : com.example.motibook
		   SHA-1 인증서 디지털 지문 : 하위 절차의 결과를 넣습니다.
	-> 8-4-1. 명령 프롬프트 혹은 콘솔을 엽니다.
	-> 8-4-2. keytool -keystore path-to-debug-or-production-keystore -list -v 후 비밀번호를 입력합니다.
		-> 8-4-2-1. 만약, keytool이 없는 명령이라는 오류가 나타난다면 https://www.oracle.com/java/technologies/javase-downloads.html 에서 Jdk를 설치합니다.
		-> 8-4-2-2. 만약, Jdk 설치 후에도 마찬가지 증상이 나타난다면, [제어판/시스템 환경 변수 편집] -> [환경변수] -> 하단 시스템 변수의 "Path" 클릭 후 [편집] -> 새로 만들기 -> [Jdk 설치경로]\bin 저장
	-> 8-4-3. 해당 결과로 SHA-1 디지털 지문이 나타나는데(20자리), 이를 복사해서 8-4의 SHA-1 인증서 디지털 지문에 넣어줍니다.


9.다시 Android Studio에서 Run -> Run 'App'을 실행합니다.
