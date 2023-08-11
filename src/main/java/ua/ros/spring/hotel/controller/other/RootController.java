package ua.ros.spring.hotel.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.ros.spring.hotel.controller.constant.ControllerConstant;

@Controller
public class RootController {
    @GetMapping("/")
    public String index() {
        return ControllerConstant.INDEX_HTML;
    }
}
