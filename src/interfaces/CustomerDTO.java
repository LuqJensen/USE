package interfaces;

import shared.Address;

/**
 *
 * @author Gruppe12
 */
public interface CustomerDTO
{

    String getID();

    String getFirstName();

    String getSurname();

    String getEmail();

    String getPhoneNumber();

    String getPersonalizedData();

    Address getDefaultDeliveryAddress();

    OrderDTO getOrder(Integer orderID);
}
