package jbehave;

import net.thucydides.jbehave.ThucydidesJUnitStories;

public class AcceptanceTestSuite extends ThucydidesJUnitStories {
    
    private static final String DEFAULT_STORY_NAMES = "**/*.story";
    
    public AcceptanceTestSuite(){
        findStoriesCalled(DEFAULT_STORY_NAMES);
    }
}
