/*
# Copyright © 2021 Argela Technologies
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
*/
package tr.com.argela.nfv.onap.serviceManager.onap.rest;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.OnapAdaptor;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
@Slf4j
public class BusinessService {

    @Autowired
    OnapAdaptor adaptor;

    @GetMapping(path = "/business/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBusinessCostumers() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER);
        log.info("[Business][Customer][Get] size:" + adaptor.getResponseSize(data, "customer"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/owning-entities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBusinessOwningEntities() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_OWNING_ENTITY);
        log.info("[Business][OwningEntity][Get] size:" + adaptor.getResponseSize(data, "owning-entity"));
        return ResponseEntity.ok(data.toString());
    }

}
