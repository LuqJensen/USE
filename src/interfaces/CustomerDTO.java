package interfaces;

import shared.Address;

/**
 *
 * @author Gruppe 12
 */
public interface CustomerDTO
{
    String getFirstName();

    String getSurname();

    String getEmail();

    String getPhoneNumber();

    String getPersonalizedData();

    String getHomeAddress();

    Address getDefaultDeliveryAddress();

    OrderDTO getOrder(Integer orderID);
}
