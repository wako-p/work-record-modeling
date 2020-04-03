package app;

import app.domain.workinghours.ClosingTime;
import app.domain.workinghours.OpeningTime;
import app.domain.workinghours.RemoteAdjustmentHours;
import app.domain.workinghours.WorkingHours;
// import java.sql.Time;

public class App {
    public static void main(final String[] args) throws Exception {

        System.out.println("Hello Java");

        final OpeningTime           opening          = OpeningTime.record();
        final ClosingTime           closing          = ClosingTime.record();
        final RemoteAdjustmentHours remoteAdjustment = RemoteAdjustmentHours.reconstruct(0.5);

        System.out.println(opening.toString());
        System.out.println(closing.toString());
        System.out.println(remoteAdjustment.toString());

        final WorkingHours working = WorkingHours.from(opening, closing, remoteAdjustment);
        System.out.println(working.toString());

    }
}
