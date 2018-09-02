package sql;

public class StampFormatter {
	private static StampFormatter instance;

	private StampFormatter() {

	}

	public static StampFormatter getInstance() {
		if (instance == null) {
			instance = new StampFormatter();
		}
		return instance;
	}

	public String format_month(Integer month) {
		String month_string = "Error";
		month += 1;
		if (month == 1) {
			month_string = "January";
		} else if (month == 2) {
			month_string = "February";
		} else if (month == 3) {
			month_string = "March";
		} else if (month == 4) {
			month_string = "April";
		} else if (month == 5) {
			month_string = "May";
		} else if (month == 6) {
			month_string = "June";
		} else if (month == 7) {
			month_string = "July";
		} else if (month == 8) {
			month_string = "August";
		} else if (month == 9) {
			month_string = "September";
		} else if (month == 10) {
			month_string = "October";
		} else if (month == 11) {
			month_string = "November";
		} else if (month == 12) {
			month_string = "December";
		}
		return month_string;
	}

	public String format_day(Integer day) {
		String day_string = "Error";

		day_string = day.toString();
		if (day_string.length() == 1) {
			day_string = "0" + day_string;
		}
		return day_string;
	}

	public String format_year(Integer year) {
		String year_string = "Error";
		year += 1900;
		year_string = year.toString();

		return year_string;
	}

	public String format_time(Integer hours, Integer seconds) {
		String time_string = "00:00 AM";
		String hr = Integer.toString(hours);
		String sec = Integer.toString(seconds);
		if(sec.length() == 1){
			sec = "0"+sec;
		}
		if (hours <= 12) {
			// AMHERE
			if (hours == 12) {
				time_string = hr + ":" + sec + " PM";
			} else {
				time_string = hr + ":" + sec + " AM";
			}
		} else {
			// PM HERE
			hr = Integer.toString(hours - 12);
			if (hours == 12) {
				time_string = hr + ":" + sec + " AM";
			} else {
				time_string = hr + ":" + sec + " PM";
			}
		}

		return time_string;
	}

}
