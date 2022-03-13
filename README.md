# Spring Converter and Formatter

# 모델 바인딩 및 검증

핸들러 메서드에 @ModelAttribute 가 지정되면 다음과 같은 일이 일어난다.

- __파라미터 타입의 오브젝트를 만든다.__
  - Ex. @ModelAttribute User user : User 라는 새로운 오브젝트를 만든다.
  - `@SessionAttributes` 에 의해 세션에 저장된 모델 오브젝트가 있다면, 세션에 저장되어 있는 오브젝트를 가져온다.
- __웹 파라미터를 오브젝트 프로퍼티에 바인딩 한다.__
  - HTTP 를 통해 전달되는 파라미터는 기본적으로 문자열 형식이라서 String 객체가 아닌 이상 적절한 변환이 필요하다.
  - 이때, 스프링에서 제공하는 기본 프로퍼티 에디터를 통해서 `HTTP Parameter -> Object Property` 로 변환한다.
  - 변환이 불가능하면 BindingResult 안에 오류를 저장해서 적절한 처리를 해야 한다.
- __모델의 값을 검증한다.__
  - 타입에 대한 검증은 끝났지만, 그 외의 검증할 내용이 있다면 적절한 검증기를 등록해서 모델의 내용을 검증할 수 있다.

> 스프링에서 바인딩이란 오브젝트의 프로퍼티에 값을 넣는 것을 말한다.

## PropertyEditor

- 스프링이 기본적으로 제공하는 바인딩용 타입 변환 API 이다.
- PropertyEditor 는 사실 스프링 API 는 아니고 자바빈 표준에 정의된 인터페이스다.
- __XML 의 value 애트리뷰트랑, @Controller 의 파라미터에 적용된다.__

```xml
<bean id="dataSource" class="org...SimpleDriverDataSource">
 <!-- com.mysql.jdbc.Driver 클래스 타입의 오브젝트를 만들어서 driverClass 라는 프로퍼티에 바인딩한다. -->
 <property name = "driverClass"  value = "com.mysql.jdbc.Driver" />
</bean>
```

```java
// CharsetEditor 사용
@RequestMapping("/hello")
public void hello(@RequestParam Charset charset, Model model) {
}
```

## References

- 토비의 스프링3
