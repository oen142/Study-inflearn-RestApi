package restapi.restapi.retrofit.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@AllArgsConstructor
@Data
public class ResponseDto {


    @SerializedName("response")
    private String response;

    @SerializedName("data")
    private ArrayList<MemberPoint> memberPoints;


    @Data
    @AllArgsConstructor
    private class MemberPoint {
        @SerializedName("memPoint")
        private int memPoint;
    }
}
