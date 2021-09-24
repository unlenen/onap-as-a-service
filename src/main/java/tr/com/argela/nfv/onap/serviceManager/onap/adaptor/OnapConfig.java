/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Component
public class OnapConfig {

    @Value("${onap.ip}")
    String onapIPAddress;
    
}
