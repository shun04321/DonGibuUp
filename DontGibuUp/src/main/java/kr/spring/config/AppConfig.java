package kr.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import kr.spring.interceptor.LoginCheckInterceptor;

//자바코드 기반 설정 클래스
@Configuration
public class AppConfig implements WebMvcConfigurer{
	private LoginCheckInterceptor loginCheck;
	
	@Bean
	public LoginCheckInterceptor interceptor2() {
		loginCheck = new LoginCheckInterceptor();
		return loginCheck;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//LoginCheckInterceptor 설정
		registry.addInterceptor(loginCheck)
		        .addPathPatterns("/member/myPage/**")
		        .addPathPatterns("/member/update")
		        .addPathPatterns("/member/changePassword")
		        .addPathPatterns("/member/delete")
		        .addPathPatterns("/board/write")
		        .addPathPatterns("/board/update")
		        .addPathPatterns("/board/delete")
		        .addPathPatterns("/category/insertCategory")
		        .addPathPatterns("/category/deleteCategory")
		        .addPathPatterns("/category/updateCategory")
				.addPathPatterns("/challenge/write")
				.addPathPatterns("/challenge/join/write")
		        .addPathPatterns("/challenge/join/list")
		        .addPathPatterns("/challenge/verify/write")
		        .addPathPatterns("/challenge/verify/list")
		        .addPathPatterns("/challenge/verify/detail")
		        .addPathPatterns("/challenge/verify/update")
		        .addPathPatterns("/challenge/verify/delete")
		        .addPathPatterns("/cs/inquiry");
	}  
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = 
				                  new TilesConfigurer();
		//XML 설정 파일 경로 지정
		configurer.setDefinitions(new String[] {
				"/WEB-INF/tiles-def/main.xml",
				"/WEB-INF/tiles-def/cyy.xml",
				"/WEB-INF/tiles-def/jsy.xml",
				"/WEB-INF/tiles-def/kbr.xml",
				"/WEB-INF/tiles-def/khc.xml",
				"/WEB-INF/tiles-def/lsy.xml",
				"/WEB-INF/tiles-def/syj.xml",
				"/WEB-INF/tiles-def/yhl.xml"
		});
		configurer.setCheckRefresh(true);
		return configurer;
	}
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = 
				                  new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
	
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}







