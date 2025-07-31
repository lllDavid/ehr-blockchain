package com.ehrblockchain.patient.mapper;

import com.ehrblockchain.patient.dto.AddressDTO;
import com.ehrblockchain.patient.dto.InsuranceDTO;
import com.ehrblockchain.patient.dto.PatientUpdateDTO;
import com.ehrblockchain.patient.model.Address;
import com.ehrblockchain.patient.model.Insurance;
import com.ehrblockchain.patient.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientMapper {

    void updateFromDto(PatientUpdateDTO dto, @MappingTarget Patient patient);

    void updateAddressFromDto(AddressDTO dto, @MappingTarget Address address);

    void updateInsuranceFromDto(InsuranceDTO dto, @MappingTarget Insurance insurance);
}
