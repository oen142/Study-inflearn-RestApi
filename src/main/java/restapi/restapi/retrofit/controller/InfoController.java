package restapi.restapi.retrofit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.restapi.retrofit.model.ResponseDto;
import restapi.restapi.retrofit.service.InternalService;

@RestController
@RequestMapping("/internal")
public class InfoController {

    private final InternalService apiService;

    public InfoController(InternalService apiService) {
        this.apiService = apiService;
    }


    @GetMapping("/info/{id}")
    public ResponseDto getInfoList(@PathVariable String id) {
        return apiService.getInternalPersonList(id).get();
    }
}
