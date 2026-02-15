CREATE TABLE clinical_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL,
    session_date DATE NOT NULL,
    session_number INTEGER,
    treatment_type VARCHAR(255),
    treatment_description TEXT,
    products_used TEXT,
    techniques_applied TEXT,
    skin_condition_before TEXT,
    skin_condition_after TEXT,
    reactions_observed TEXT,
    patient_feedback TEXT,
    home_care_recommendations TEXT,
    next_session_recommendations TEXT,
    products_recommended TEXT,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_clinical_session_patient FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

CREATE INDEX idx_clinical_sessions_patient ON clinical_sessions(patient_id);
CREATE INDEX idx_clinical_sessions_date ON clinical_sessions(session_date);
CREATE INDEX idx_clinical_sessions_patient_date ON clinical_sessions(patient_id, session_date);
