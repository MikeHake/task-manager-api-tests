package jbehave;

import net.thucydides.jbehave.ThucydidesJUnitStories;

public class AcceptanceTestSuite extends ThucydidesJUnitStories {
    
    private static final String DEFAULT_STORY_NAMES = "**/*.story";
    //private static final String DEFAULT_STORY_NAMES = "**/task_instance_post.story";
    
    public AcceptanceTestSuite(){
        findStoriesCalled(DEFAULT_STORY_NAMES);
    }
}
