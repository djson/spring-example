# Swagger 3.0 Sample

This is Swagger 3.0 java & Spring Boot Api Sample

# Used Annotation From Swagger v3
> 이 프로젝트에서 사용한 Swagger Annotation에 대해 간략하게 기술합니다.
- @Tag
    - API 엔드 포인트를 묶을 그룹 태그를 지정한다.
    - 컨트롤러 파일 최상단 및 각 컨트롤러에 동일한 태그를 지정할 시, 같은 그룹으로 묶인다.

- @ApiOperation
    - API의 동작을 정의한다.
        - value : API 제목
        - notes : API 상세 설명
        - authorizations : 권한 키 맵핑 묶는 상위 어노테이션
- @Authorization
    - 권한 설정 지정 > apiKey/Bearer JWT 등

- @ApiResponses
    - API의 응답 정의들을 묶는 상위 어노테이션
- @ApiResponse
    - 실제 API 응답 처리 설정 
    - responseCode : 응답코드 지정
    - description : 응답 설명
    - content : 응답에 포함된 내용 정의(스키마 지정 혹은 직접 하드코딩으로 설정)

- @Content(schema = @Schema(implementation = T.class))
    - 응답 클래스의 내용으로 들어갈 특정 스키마를 지정

- @Parameter
    - 컨트롤러 파라미터에 대한 설명 지정
        - name : 이름
        - description : 설명
        - example : 예시
        - required : 필수여부 (true/false)

- @Schema
    - 해당 클래스 또는 멤버 변수를 스키마 또는 스키마 내부 멤버로 지정한다.
        - name : 스키마 명
        - description : 스키마 설명
        - defaultValue : 기본 값
        - allowableValues : 허용 값(배열로 지정)
        - example : 예시 값 (문서에서 해당 변수의 값으로 지정되어 나옴)