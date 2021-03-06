package com.example.demo;

import com.example.demo.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@DataJpaTest
public class VehicleJpaTest {

    @Autowired
    private TestEntityManager tem;

    @Test
    public void mapping() {
        Vehicle v = this.tem.persistFlushFind(Vehicle.builder().name("test").build());
        assertThat(v.getName()).isEqualTo("test");
        assertThat(v.getId()).isNotNull();
        assertThat(v.getId()).isGreaterThan(0);
        //assertThat(v.getCreatedDate()).isNotNull();
    }
}
