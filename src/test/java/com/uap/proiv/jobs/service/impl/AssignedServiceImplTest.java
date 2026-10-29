package com.uap.proiv.jobs.service.impl;

import com.uap.proiv.jobs.dto.AssignedResponse;
import com.uap.proiv.jobs.dto.Job;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignedServiceImplTest {

    private final AssignedServiceImpl service = new AssignedServiceImpl();

    @Test
    void createAssignsRequestedNumberOfUsersToEachJob() {
        Job job = new Job();
        job.setId(7);
        job.setResources(2);

        List<AssignedResponse> result = service.create(List.of(job), new ArrayList<>(List.of(10, 20)));

        assertEquals(2, result.size());
        assertEquals(7, result.get(0).jobId());
        assertEquals(7, result.get(1).jobId());
        assertEquals(List.of(10, 20), result.stream()
            .map(AssignedResponse::userId)
                .sorted()
                .toList());
    }
}
