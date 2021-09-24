/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor.exception;

import java.io.IOException;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class OnapRequestFailedException extends IOException {

    OnapRequest onapRequest;

    public OnapRequestFailedException(OnapRequest onapRequest) {
        this.onapRequest = onapRequest;
    }

    public OnapRequestFailedException(OnapRequest onapRequest, String string) {
        super(string);
        this.onapRequest = onapRequest;
    }

    public OnapRequestFailedException(OnapRequest onapRequest, String string, Throwable thrwbl) {
        super(string, thrwbl);
        this.onapRequest = onapRequest;
    }

    public OnapRequest getOnapRequest() {
        return onapRequest;
    }

}
