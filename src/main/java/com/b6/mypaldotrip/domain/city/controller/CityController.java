package com.b6.mypaldotrip.domain.city.controller;

import com.b6.mypaldotrip.domain.city.controller.dto.request.CityCreateReq;
import com.b6.mypaldotrip.domain.city.controller.dto.request.CityUpdateReq;
import com.b6.mypaldotrip.domain.city.controller.dto.response.CityCreateRes;
import com.b6.mypaldotrip.domain.city.controller.dto.response.CityDeleteRes;
import com.b6.mypaldotrip.domain.city.controller.dto.response.CityListRes;
import com.b6.mypaldotrip.domain.city.controller.dto.response.CityUpdateRes;
import com.b6.mypaldotrip.domain.city.service.CityService;
import com.b6.mypaldotrip.global.common.GlobalResultCode;
import com.b6.mypaldotrip.global.config.VersionConfig;
import com.b6.mypaldotrip.global.response.RestResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;
    private final VersionConfig versionConfig;

    @PostMapping//생성
    public ResponseEntity<RestResponse<CityCreateRes>> createCity(
        @RequestBody CityCreateReq cityCreateReq
        //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CityCreateRes res = cityService.createCity(cityCreateReq);
        //userDetails.getUser());
        return RestResponse.success(res, GlobalResultCode.CREATED, versionConfig.getVersion())
            .toResponseEntity();

    }

    @PutMapping("/{cityId}")//수정
    public ResponseEntity<RestResponse<CityUpdateRes>> updateCity(
        @PathVariable Long cityId,
        @RequestBody CityUpdateReq cityUpdateReq
        //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CityUpdateRes res = cityService.updateCity(cityId,cityUpdateReq);
        //userDetails.getUser());
        return RestResponse.success(res, GlobalResultCode.CREATED, versionConfig.getVersion())
            .toResponseEntity();

    }

    @DeleteMapping("/{cityId}")//삭제
    public ResponseEntity<RestResponse<CityDeleteRes>> deleteCity(@PathVariable Long cityId
        //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CityDeleteRes res = cityService.deleteCity(cityId);
        //userDetails.getUser());
        return RestResponse.success(res, GlobalResultCode.CREATED, versionConfig.getVersion())
            .toResponseEntity();

    }

    @GetMapping//도시 전체 조회
    public ResponseEntity<RestResponse<List<CityListRes>>> getCourseList() {
        List<CityListRes> res = cityService.getCityList();

        return RestResponse.success(res, GlobalResultCode.SUCCESS, versionConfig.getVersion())
            .toResponseEntity();
    }

}
