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

### PropertyEditor 에서 제공하는 메서드

> HTTP 요청 파라미터와 같은 문자열은 스트링 타입으로 서블릿에서 가져온다.

- __String to Object__
  - setAsText() 로 String 타입의 문자열을 넣고 getValue() 로 변환된 오브젝트를 가져온다.
- __Object to String__
  - setValue() 로 오브젝트를 넣고 getAsText() 로 변환된 문자열을 가져온다.

따라서, 커스텀 프로퍼티 에디터를 만들 때는, setAsText(), getAsText() 부분만 손보면 된다.

### 커스텀 프로퍼티 에디터

> PropertyEditor 인터페이스를 직접 구현하기보다는 기본 구현이 되어있는 PropertyEditorSupport 클래스를 상속해서 필요한 메서드만 오버라이딩 하는게 낫다.

```kotlin
class LevelPropertyEditor: PropertyEditorSupport() {

    override fun getAsText(): String {
        // this.value -> setValue 에 의해 저장된 Level 타입의 오브젝트를 가져와서 값을 문자로 변환한다.
        return ((this.value as Level).intValue().toString())
    }

    override fun setAsText(text: String?) {
        this.value = text?.trim()?.let { Level.valueOf(it.toInt()) }
    }
}
```

만든 커스텀 프로퍼티 에디터가 스프링 MVC 에서 동작하게 하려면 `@InitBinder` 를 사용하면 된다.

## @InitBinder

- __컨트롤러 메서드에서 바인딩이 어떻게 일어날까?__
  - @Controller 핸들러 메서드를 호출해줄 책임이 있는 `AnnotationMethodHandlerAdapter` 는 @RequestParam, @ModelAttribute 등 HTTP 요청을 파라미터 변수에 바인딩 해주는 작업이 필요한 어노테이션을 만나면 먼저 `WebDataBinder` 라는 것을 만든다.
- __WebDataBinder__
  - HTTP 요청으로부터 가져온 문자열을 파라미터 타입의 오브젝트로 변환해주는 기능이 있다. 이때 `PropertyEditor` 를 사용하는 것이다.
  - 따라서, 커스텀 프로퍼티 에디터를 사용하기 위해서는 WebDataBinder 에 등록해줘야 한다.
  - WebDataBinder 는 커스텀 프로퍼티 에디터가 있으면 먼저 적용하고, 적절한 프로퍼티 에디터가 없다면 그때 스프링에서 제공하는 디폴트 프로퍼티 에디터중 하나를 사용하게 된다.

```kotlin
@RestController
class ConversionController {

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.registerCustomEditor(Level::class.java, LevelPropertyEditor())
    }
    
    @GetMapping("/level")
    fun levelCustomEditor(@RequestParam level: Level): Int {
        return level.intValue()
    }
}
```

## 프로토타입 빈 프로퍼티 에디터

오브젝트를 매번 새로 만드는 대신 프로퍼티 에디터를 싱글톤 빈으로 등록해두고 공유해서 쓸 수 없을까? 아쉽지만 __프로퍼티 에디터는 싱글톤 빈으로 등록될 수 없다.__ 
프로퍼티 에디터에 의해 타입이 변경되는 오브젝트는 한 번은 프로퍼티 에디터 오브젝트 `내부에 저장`된다는 사실을 알 수 있다. __따라서, 멀티 스레드 환경에서 싱글톤으로 만들어 공유해서 사용하면 안된다.__

그런데 프로퍼티 에디터가 다른 스프링 빈을 참조해야 한다면 어떨까?

다른 빈을 참조해서 DI 받으려면 자신도 스프링 빈으로 등록되어야 한다. 이를 위해 프로퍼티 에디터가 다른 빈을 DI 받을 수 있도록 자신도 빈으로서 등록되면서 동시에 매번 새로운 오브젝트를 만들어서사용할 수 있으려면 `프로토타입 스코프의 빈`으로 만들어져야 한다.

__프로토타입 스코프 빈은 매번 빈 오브젝트를 요청해서 새로운 오브젝트를 가져올 수 있으면서 DI 도 가능하다.__

## References

- 토비의 스프링3
