package TradingSystem.server.Domain.StoreModule.Store;

import TradingSystem.server.Domain.StoreModule.Appointment.AppointmentInformation;


import java.util.List;

public class StoreManagersInfo {
    private String store_name;
    private List<AppointmentInformation> appointmentInformationList;

    // ------------------------------ constructors ------------------------------
    public StoreManagersInfo(String store_name, List<AppointmentInformation> appointmentInformationList) {

        this.store_name = store_name;
        this.appointmentInformationList = appointmentInformationList;
    }

    public StoreManagersInfo() {
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setAppointmentInformationList(List<AppointmentInformation> appointmentInformationList) {
        this.appointmentInformationList = appointmentInformationList;
    }

    public String getStore_name() {
        return store_name;
    }

    public List<AppointmentInformation> getAppointmentInformationList() {
        return appointmentInformationList;
    }

    // ------------------------------ getters ------------------------------



    // ------------------------------ setters ------------------------------



    // ------------------------------ methods ------------------------------


}
