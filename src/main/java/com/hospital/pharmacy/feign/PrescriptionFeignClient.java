package com.hospital.pharmacy.feign;

import com.hospital.pharmacy.dto.PrescriptionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(
//        name = "prescription-service",
//        url = "http://localhost:8022"   // prescription service port
//)
//public interface PrescriptionFeignClient {
//
//   @GetMapping("/api/prescriptions/{id}")
//    PrescriptionResponseDTO getPrescription(
//            @PathVariable("id") Long id,
//           @RequestHeader("Authorization") String token
//    );
//	@GetMapping("/api/prescriptions/{id}")
//	PrescriptionResponseDTO getPrescription(@PathVariable("id") Long id);
//}


@FeignClient(
        name = "prescription-service",
        url = "http://localhost:8666"
)
public interface PrescriptionFeignClient {

	@GetMapping("/prescriptions/with-items/{id}")
    PrescriptionResponseDTO getPrescription(
            @PathVariable("id") Long id
    );
}