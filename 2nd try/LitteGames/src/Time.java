import java.time.Instant;
import java.time.Duration;

public class Time {
	Instant start;
	public Time() {
		start=Instant.now();
	}
	public long getDuration() {
		return Duration.between(start, Instant.now()).toMillis();
	}
}
