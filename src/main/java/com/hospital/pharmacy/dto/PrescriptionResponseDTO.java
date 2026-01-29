package com.hospital.pharmacy.dto;

import java.util.List;

public class PrescriptionResponseDTO {

    private Long prescriptionId;
    private Long patientId;
    private List<PrescriptionMedicineDTO> medicines;

    public Long getPrescriptionId() {
        return prescriptionId;
    }

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public List<PrescriptionMedicineDTO> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<PrescriptionMedicineDTO> medicines) {
		this.medicines = medicines;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

    
}