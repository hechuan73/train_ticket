package security.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import security.domain.CreateSecurityConfigInfo;
import security.service.SecurityService;

@Component
public class initData implements CommandLineRunner {

    @Autowired
    private SecurityService securityService;

    @Override
    public void run(String... args) throws Exception {
        CreateSecurityConfigInfo info1 = new CreateSecurityConfigInfo();
        info1.setName("max_order_1_hour");
        info1.setValue("5");
        info1.setDescription("一个小时内最多下达的订单");
        securityService.addNewSecurityConfig(info1);
        CreateSecurityConfigInfo info2 = new CreateSecurityConfigInfo();
        info2.setName("max_order_not_use");
        info2.setValue("20");
        info2.setDescription("最多可持有的未使用票数");
        securityService.addNewSecurityConfig(info2);
    }
}
