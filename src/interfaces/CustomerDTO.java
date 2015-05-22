package interfaces;

import shared.Address;

/**
 *
 * @author Gruppe12
 */
public interface CustomerDTO
{

    /**
     * Gets the ID of the customer.
     *
     * @return The ID of the customer
     */
    String getID();

    /**
     * Gets the first name of the customer
     *
     * @return The first name of the customer
     */
    String getFirstName();

    /**
     * Gets the surname of the customer.
     *
     * @return The surname of the customer.
     */
    String getSurname();

    /**
     * Gets the email of the customer.
     *
     * @return The email of the customer
     */
    String getEmail();

    /**
     * Gets the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    String getPhoneNumber();

    /**
     * Gets personalized data about the customer.
     *
     * @return Personalized data about the customer.
     */
    String getPersonalizedData();

    /**
     * Gets the default delivery address of the customer
     *
     * @return The default delivery address of the customer.
     */
    Address getDefaultDeliveryAddress();

    /**
     * Gets the specified order of the customer by order ID.
     *
     * @param orderID The orders ID.
     * @return Returns a orderDTO.
     */
    OrderDTO getOrder(Integer orderID);
}
