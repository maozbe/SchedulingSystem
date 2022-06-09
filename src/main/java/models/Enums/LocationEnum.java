package models.Enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum LocationEnum { //TODO: can be taken using api for all the cities in the country
  BEERSHEVA("Beer Sheva",25.2234,51.2141),
  ASHDOD("Ashdod",14.2234,24.2141),
  JERUSALEM("Jerusalem",41.2234,28.2141),
  YAVNE("Yavne",17.5156,33.4452),
  TELAVIV("Tel Aviv",20.2234,41.2141),
  HAIFA("Haifa",13.2234,49.2141),
  ;
  private final String cityName;
  private final double Xaxis;
  private final double Yaxis;


  public String getCityName() {
    return cityName;
  }

  public double getXaxis() {
    return Xaxis;
  }

  public double getYaxis() {
    return Yaxis;
  }


      LocationEnum(String cityName, double Xaxis, double Yaxis) {
            this.cityName = cityName;
            this.Xaxis = Xaxis;
            this.Yaxis = Yaxis;
          }


    private static final List<LocationEnum> VALUES =
        Collections.unmodifiableList(Arrays.asList(LocationEnum.values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static LocationEnum randomLocation()  {
      return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

