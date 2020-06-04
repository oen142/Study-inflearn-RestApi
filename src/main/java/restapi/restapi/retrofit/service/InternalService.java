package restapi.restapi.retrofit.service;

import org.springframework.stereotype.Service;
import restapi.restapi.retrofit.model.ResponseDto;
import restapi.restapi.retrofit.util.RequestUtil;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class InternalService {

    private InfoAPI infoAPI;

    @PostConstruct
    public void init() {
        infoAPI = RequestUtil.createService(InfoAPI.class);
    }

    public Optional<ResponseDto> getInternalPersonList(String id) {
        Optional<ResponseDto> response = RequestUtil.requestSync(infoAPI.getInfoList(id));
        return response;
    }

    public ResponseDto getResponse(String name){
        Optional<ResponseDto> responseDto = RequestUtil.requestSync(infoAPI.getInfo(name));
        return responseDto.get();
    }

}
