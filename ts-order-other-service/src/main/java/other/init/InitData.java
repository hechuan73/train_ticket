package other.init;

import other.service.OrderOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    OrderOtherService service;

    public void run(String... args)throws Exception{

    }

}
