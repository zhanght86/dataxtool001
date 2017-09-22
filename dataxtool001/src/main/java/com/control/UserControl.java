package com.control;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.domain.User;

/**
 * 用来处理前端传来的请求
 * @author wang
 *
 */
//在配置文件中定义了组件的扫描，
@Controller
public class UserControl {
	//处理 user的请求，但是方法必须是post
	@RequestMapping("/user.do")
	public ModelAndView createUser(@RequestBody User user) {//user的参数会自动的绑定
		System.out.println(user.getUsername());
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("user/success");
		modelAndView.addObject("user",user);
		return modelAndView;
	}

}
