package server.resources;

import resources.TestResource;

public class TestResourceServerController implements TestResourceServerControllerMBean {

    private final TestResource resource;

    public TestResourceServerController(TestResource testResource) {
        resource = testResource;
    }

    @Override
    public String getName() {
        return resource.getName();
    }

    @Override
    public int getAge() {
        return resource.getAge();
    }

}
