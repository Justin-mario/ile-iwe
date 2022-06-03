package com.ileiwe.service.events;

import com.ileiwe.data.model.LearningParty;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private LearningParty appUser;
    public OnRegistrationCompleteEvent(LearningParty source) {
        super ( source );
        this.appUser = source;
    }
}
