package com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class viaSMS implements IObserver{
    private String type;



    // Find your Account Sid and Auth Token at twilio.com/console
    private static final String ACCOUNT_SID =
            "AC8efdf9a056e40ec20dd248b653efaf9e";
    private static final String AUTH_TOKEN =
            "2f0bf61f1c458d1f5f6facf1ba516893";



    public viaSMS() {
        this.type = "SMS";
    }


    public String getType() {
        return type;
    }

    @Override
    public void update(String Phonenumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+1"+Phonenumber), // to
                        new PhoneNumber("+17087616084"), // from
                        "Room is Ready")
                .create();

        System.out.println(message.getSid());
        System.out.println("Sending SMS");
    }
}
