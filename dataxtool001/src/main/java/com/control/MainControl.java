package com.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 用于前台数据的显示，前台的各种菜单都是通过该控制器来获得需要的数据
 * @author Johnny
 *
 */
@Controller
public class MainControl {
	/**
	 * 查询得到一级菜单
	 * @return
	 */
	@RequestMapping("/firstmenu.do")
	public ModelAndView findFirstMenu() {
		System.out.println("firstmenu");
		return new ModelAndView("reader.jsp");
	}
}
