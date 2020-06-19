package com.raj.logistics.ratemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.raj.logistics.ratemanagement.entity.Rate;
import java.sql.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RatemanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RateManagementControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetRateById() {
        Rate rate = restTemplate.getForObject(getRootUrl() + "/ratemanagement/searchRate/1", Rate.class);
        System.out.println(rate.getRateId());
        assertNotNull(rate);
    }

    @Test
    public void testSaveRate() {
        Rate rate = new Rate();
        rate.setRateId(1l);
        rate.setAmount(5000);
        rate.setRateDesc("Raj Test1");
        rate.setRateEffectiveDate(new Date(System.currentTimeMillis()));
        rate.setRateExpirationDate(new Date(System.currentTimeMillis()));
        ResponseEntity<Rate> postResponse = restTemplate.postForEntity(getRootUrl() + "/ratemanagement/saveRate", rate, Rate.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateRate() {
        int id = 1;
        Rate rate = restTemplate.getForObject(getRootUrl() + "/ratemanagement/searchRate/"+id, Rate.class);
        rate.setAmount(10000);
        rate.setRateDesc("Raj Test2");
        rate.setRateEffectiveDate(new Date(System.currentTimeMillis()));
        rate.setRateExpirationDate(new Date(System.currentTimeMillis()));
        restTemplate.put(getRootUrl() + "/ratemanagement/updateRate", rate);
        Rate updatedRate = restTemplate.getForObject(getRootUrl() + "/ratemanagement/searchRate/"+id, Rate.class);
        assertNotNull(updatedRate);
    }

    @Test
    public void testDeleteRate() {
        int id = 1;
        Rate rate = restTemplate.getForObject(getRootUrl() + "/ratemanagement/searchRate/"+id, Rate.class);
        assertNotNull(rate);
        restTemplate.delete(getRootUrl() + "/ratemanagement/deleteRate/" + id);
        try {
            rate = restTemplate.getForObject(getRootUrl() + "/ratemanagement/searchRate/" + id, Rate.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}