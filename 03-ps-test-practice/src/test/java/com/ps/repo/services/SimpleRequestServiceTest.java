package com.ps.repo.services;

import com.ps.base.RequestStatus;
import com.ps.base.UserType;
import com.ps.ents.Request;
import com.ps.ents.User;
import com.ps.repos.RequestRepo;
import com.ps.services.impl.SimpleRequestService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.ps.util.TestObjectsBuilder.*;

/**
 * Created by iuliana.cosmina on 4/17/16.
 * kmandawe: USING JMock
 */
public class SimpleRequestServiceTest {

    public static final Long REQUEST_ID = 1L;

    // 1. Declare the mock
    private RequestRepo requestMockRepo;

    // 2. Declare and define the context of the object under test
    private Mockery mockery = new Mockery();

    private SimpleRequestService simpleRequestService;

    @Before
    public void setUp() {
        // 3. Create the mock
        requestMockRepo = mockery.mock(RequestRepo.class);

        simpleRequestService = new SimpleRequestService();
        // 4. Inject the mock
        simpleRequestService.setRepo(requestMockRepo);
    }

    @Test
    public void findByIdPositive() {
        Request req = new Request();
        req.setId(REQUEST_ID);
        req.setStartAt(DateTime.parse("2016-09-06").toDate());
        req.setEndAt(DateTime.parse("2016-09-18").toDate());
        req.setRequestStatus(RequestStatus.NEW);

        // 5. Define the expectations we have from the mock
        mockery.checking(new Expectations() {{
            allowing(requestMockRepo).findById(REQUEST_ID);
            will(returnValue(req));
        }});

        // 6. Test
        Request result = simpleRequestService.findById(REQUEST_ID);
        // 7. Check that mock was actually used
        mockery.assertIsSatisfied();
        // 8. Validate the execution
        assertNotNull(result);
        assertEquals(req.getId(), result.getId());
    }


    @Test
    public void findByUserPositive() {
        User user = buildUser("gigi@gmail.com", "1!2#tre", UserType.OWNER);
        Request req = new Request();
        req.setUser(user);
        req.setStartAt(DateTime.parse("2016-09-06").toDate());
        req.setEndAt(DateTime.parse("2016-09-18").toDate());
        req.setRequestStatus(RequestStatus.NEW);
        Set<Request> requestSet= new HashSet<>();
        requestSet.add(req);

        mockery.checking(new Expectations() {{
            allowing(requestMockRepo).findAllForUser(user);
            will(returnValue(requestSet));
        }});

        Set<Request> result = simpleRequestService.findAllByUser(user);
        assertEquals(result.size(), 1);
    }

}
