package com.hyrt.cnp.base.account.model;

import java.io.Serializable;

/**
 * Created by Zoe on 2014-04-05.
 */
public class PositionInfo implements Serializable {

    private Location location;

    public class Location{
        private double lng;
        private double lat;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) return false;

            Location location1 = (Location) o;
            if(lng != location1.lng){
                return false;
            }
            if(lat != location1.lat){
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) lng;
            result = (int) (31 * result + lng);
            return result;
        }
    }

    private String formatted_address;

    private String business;

    private AddressComponent addressComponent;

    public class AddressComponent{
        private String city;
        private String district;
        private String province;
        private String street;
        private String street_number;

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) return false;

            AddressComponent addressComponent1 = (AddressComponent) o;

            if (city != null ? !city.equals(addressComponent1.city) : addressComponent1.city != null) return false;
            if (district != null ? !district.equals(addressComponent1.district) : addressComponent1.district != null) return false;
            if (province != null ? !province.equals(addressComponent1.province) : addressComponent1.province != null) return false;
            if (street != null ? !street.equals(addressComponent1.street) : addressComponent1.street != null) return false;
            if (street_number != null ? !street_number.equals(addressComponent1.street_number)
                    : addressComponent1.street_number != null) {return false;}

            return true;
        }

        @Override
        public int hashCode() {
            int result = (city != null ? city.hashCode() : 0);
            result = 31 * result + (district != null ? district.hashCode() : 0);
            result = 31 * result + (province != null ? province.hashCode() : 0);
            result = 31 * result + (street != null ? street.hashCode() : 0);
            result = 31 * result + (street_number != null ? street_number.hashCode() : 0);

            return result;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreet_number() {
            return street_number;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }
    }

    private int cityCode;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) return false;

        PositionInfo positionInfo = (PositionInfo) o;
        if (location != null ? !location.equals(positionInfo.location) : positionInfo.location != null) return false;
        if (formatted_address != null ? !formatted_address.equals(positionInfo.formatted_address)
                : positionInfo.formatted_address != null) {return false;}
        if (business != null ? !business.equals(positionInfo.business) : positionInfo.business != null) {return false;}
        if (addressComponent != null ? !addressComponent.equals(positionInfo.addressComponent)
                : positionInfo.addressComponent != null) {return false;}
        if(cityCode != positionInfo.cityCode){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (location != null ? location.hashCode() : 0);
        result = 31 * result + (formatted_address != null ? formatted_address.hashCode() : 0);
        result = 31 * result + (business != null ? business.hashCode() : 0);
        result = 31 * result + (addressComponent != null ? addressComponent.hashCode() : 0);
        result = 31 * result + cityCode;

        return result;
    }

    public static class Model extends Base {
        private static final int serialVersionUID = -1;
        private PositionInfo result;

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) {return false;}
            if (!super.equals(o)) {return false;}

            Model model = (Model) o;
            if (result != null ? !result.equals(model.result) : model.result != null) {return false;}

            return true;
        }

        @Override
        public int hashCode() {
            int result2 = super.hashCode();
            result2 = 31 * result2 + (result != null ? result.hashCode() : 0);
            return result2;
        }

        public PositionInfo getData() {
            return result;
        }

        public void setData(PositionInfo data) {
            this.result = data;
        }
    }
}
