/**
 * Copyright (c) 2014 - 2017, Nordic Semiconductor ASA
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form, except as embedded into a Nordic
 *    Semiconductor ASA integrated circuit in a product or a software update for
 *    such product, must reproduce the above copyright notice, this list of
 *    conditions and the following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 * 
 * 3. Neither the name of Nordic Semiconductor ASA nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 * 
 * 4. This software, with or without modification, must only be used with a
 *    Nordic Semiconductor ASA integrated circuit.
 * 
 * 5. Any software provided in binary form under this license must not be reverse
 *    engineered, decompiled, modified and/or disassembled.
 * 
 * THIS SOFTWARE IS PROVIDED BY NORDIC SEMICONDUCTOR ASA "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NONINFRINGEMENT, AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL NORDIC SEMICONDUCTOR ASA OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
/** @file
 *
 * @defgroup blinky_example_main main.c
 * @{
 * @ingroup blinky_example
 * @brief Blinky Example Application main file.
 *
 * This file contains the source code for a sample application to blink LEDs.
 *
 */

#include <stdbool.h>
#include <stdint.h>
#include "nrf_delay.h"
#include "boards.h"


#include "nrf.h"
#include "nrf_drv_gpiote.h"
#include "app_error.h"


//Tutorial1 = https://devzone.nordicsemi.com/f/nordic-q-a/19242/configuration-of-digital-input-output-pins-on-nrf52

//------------------ From Tutorial1 ------------------
#define GPIO_PIN_17     17
#define GPIO_PIN_18     18
#define GPIO_PIN_19     19
#define GPIO_PIN_20     20
#define GPIO_PIN_21     21
#define GPIO_PIN_22     22
#define GPIO_PIN_23     23
#define GPIO_PIN_24     24
#define GPIO_PIN_25     25
#define GPIO_PIN_26     26

#define GPIO_PIN_27     27
#define GPIO_PIN_28     28
#define GPIO_PIN_29     29
#define GPIO_PIN_30     30

#define S_ON     1
#define S_OFF    0

//Forum1=https://devzone.nordicsemi.com/f/nordic-q-a/20971/hi-can-anyone-please-tell-me-on-how-to-read-the-digital-input-value-from-pir-sensor-in-nrf52832
//------------------ From Forum1-END------------------

/*
uint32_t pin_value_0;
uint32_t pin_value_1;
uint32_t pin_value_2;
uint32_t pin_value_3;
uint32_t pin_value_21;
uint32_t pin_value_22;
uint32_t pin_value_23;
uint32_t pin_value_24;
uint32_t pin_value_25;
uint32_t pin_value_26;
uint32_t pin_value_27;*/

void startSensors(void)
{
  nrf_gpio_cfg_input (BSP_BUTTON_0, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (BSP_BUTTON_1, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (BSP_BUTTON_2, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (BSP_BUTTON_3, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_21, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_22, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_23, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_24, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_25, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_26, NRF_GPIO_PIN_NOPULL);
  nrf_gpio_cfg_input (GPIO_PIN_27, NRF_GPIO_PIN_NOPULL);

 /* pin_value_0 = nrf_gpio_pin_read (BSP_BUTTON_0);
  pin_value_1 = nrf_gpio_pin_read (BSP_BUTTON_1);
  pin_value_2 = nrf_gpio_pin_read (BSP_BUTTON_2);
  pin_value_3 = nrf_gpio_pin_read (BSP_BUTTON_3);
  pin_value_21 = nrf_gpio_pin_read (GPIO_PIN_21);
  pin_value_22 = nrf_gpio_pin_read (GPIO_PIN_22);
  pin_value_23 = nrf_gpio_pin_read (GPIO_PIN_23);
  pin_value_24 = nrf_gpio_pin_read (GPIO_PIN_24);
  pin_value_25 = nrf_gpio_pin_read (GPIO_PIN_25);
  pin_value_26 = nrf_gpio_pin_read (GPIO_PIN_26);
  pin_value_27 = nrf_gpio_pin_read (GPIO_PIN_27);*/
}


//------------------ From Forum1-END------------------


void gpio_init_group(void)
{ 
   // Pins as outputs
   //nrf_gpio_range_cfg_input(27,30,NRF_GPIO_PIN_PULLUP);
   nrf_gpio_range_cfg_input(0,30,NRF_GPIO_PIN_PULLUP);
   
   // Pins as inputs
   //nrf_gpio_range_cfg_output(17, 24);
			
}
//------------------ From Tutorial1-END------------------



//From examples/peripherical/uart
#include <stdio.h>
#include "app_uart.h"
#include "app_error.h"
#include "bsp.h"
#if defined (UART_PRESENT)
#include "nrf_uart.h"
#endif


//From examples/peripherical/uart - BEGIN
//#define ENABLE_LOOPBACK_TEST  /**< if defined, then this example will be a loopback test, which means that TX should be connected to RX to get data loopback. */

#define MAX_TEST_DATA_BYTES     (15U)                /**< max number of test bytes to be used for tx and rx. */
#define UART_TX_BUF_SIZE 256                         /**< UART TX buffer size. */
#define UART_RX_BUF_SIZE 256                         /**< UART RX buffer size. */

void uart_error_handle(app_uart_evt_t * p_event)
{
    if (p_event->evt_type == APP_UART_COMMUNICATION_ERROR)
    {
        APP_ERROR_HANDLER(p_event->data.error_communication);
    }
    else if (p_event->evt_type == APP_UART_FIFO_ERROR)
    {
        APP_ERROR_HANDLER(p_event->data.error_code);
    }
}

#ifdef ENABLE_LOOPBACK_TEST
/* Use flow control in loopback test. */
#define UART_HWFC APP_UART_FLOW_CONTROL_ENABLED
/** @brief Function for setting the @ref ERROR_PIN high, and then enter an infinite loop.
 */

// @brief Function for setting the @ref ERROR_PIN high, and then enter an infinite loop. 
static void show_error(void)
{

    bsp_board_leds_on();
    while (true)
    {
        // Do nothing.
    }
}

/** @brief Function for testing UART loop back.
 *  @details Transmitts one character at a time to check if the data received from the loopback is same as the transmitted data.
 *  @note  @ref TX_PIN_NUMBER must be connected to @ref RX_PIN_NUMBER)
 */
static void uart_loopback_test()
{
    uint8_t * tx_data = (uint8_t *)("\r\nLOOPBACK_TEST\r\n");
    uint8_t   rx_data;

    // Start sending one byte and see if you get the same
    for (uint32_t i = 0; i < MAX_TEST_DATA_BYTES; i++)
    {
        uint32_t err_code;
        while (app_uart_put(tx_data[i]) != NRF_SUCCESS);

        nrf_delay_ms(10);
        err_code = app_uart_get(&rx_data);

        if ((rx_data != tx_data[i]) || (err_code != NRF_SUCCESS))
        {
            show_error();
        }
    }
    return;
}
#else
/* When UART is used for communication with the host do not use flow control.*/
#define UART_HWFC APP_UART_FLOW_CONTROL_DISABLED
#endif


//From examples/peripherical/uart - END


#ifdef BSP_BUTTON_0
    #define PIN_IN BSP_BUTTON_0
#endif

//#ifdef  ARDUINO_10_PIN
//    #define PIN_IN ARDUINO_10_PIN
//#endif

#ifndef PIN_IN
    #error "Please indicate input pin"
#endif

#ifdef BSP_LED_3
    #define PIN_OUT BSP_LED_3
#endif
#ifndef PIN_OUT
    #error "Please indicate output pin"
#endif

void in_pin_handler(nrf_drv_gpiote_pin_t pin, nrf_gpiote_polarity_t action)
{
    nrf_drv_gpiote_out_toggle(PIN_OUT);
}

/**
 * @brief Function for configuring: PIN_IN pin for input, PIN_OUT pin for output,
 * and configures GPIOTE to give an interrupt on pin change.
 */
static void gpio_init(void)
{
    ret_code_t err_code;

    err_code = nrf_drv_gpiote_init();
    APP_ERROR_CHECK(err_code);

    nrf_drv_gpiote_out_config_t out_config = GPIOTE_CONFIG_OUT_SIMPLE(false);

    err_code = nrf_drv_gpiote_out_init(PIN_OUT, &out_config);
    APP_ERROR_CHECK(err_code);

    nrf_drv_gpiote_in_config_t in_config = GPIOTE_CONFIG_IN_SENSE_TOGGLE(true);
    in_config.pull = NRF_GPIO_PIN_PULLUP;

    err_code = nrf_drv_gpiote_in_init(PIN_IN, &in_config, in_pin_handler);
    APP_ERROR_CHECK(err_code);

    nrf_drv_gpiote_in_event_enable(PIN_IN, true);
}

void printSensorState(void) {
  printf(" \t%d", BSP_BUTTON_0);
  printf(" \t%d", BSP_BUTTON_1);
  printf(" \t%d", BSP_BUTTON_2);
  printf(" \t%d", BSP_BUTTON_3);
  printf(" \t%d", GPIO_PIN_21);
  printf(" \t%d", GPIO_PIN_22);
  printf(" \t%d", GPIO_PIN_23);
  printf(" \t%d", GPIO_PIN_24);
  printf(" \t%d", GPIO_PIN_25);
  printf(" \t%d", GPIO_PIN_26);
  printf(" \t%d", GPIO_PIN_27);
  printf(" \n");
  printf(" \t%lu", nrf_gpio_pin_read(BSP_BUTTON_0));
  printf(" \t%lu", nrf_gpio_pin_read(BSP_BUTTON_1));
  printf(" \t%lu", nrf_gpio_pin_read(BSP_BUTTON_2));
  printf(" \t%lu", nrf_gpio_pin_read(BSP_BUTTON_3));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_21));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_22));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_23));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_24));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_25));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_26));
  printf(" \t%lu", nrf_gpio_pin_read(GPIO_PIN_27));
  printf(" \n");
}


/**
 * @brief Function for application main entry.
 */
 int main(void)
 {
     /* FROM /examples/peripherical/uart */
    uint32_t err_code;

    bsp_board_leds_init();

    const app_uart_comm_params_t comm_params =
      {
          RX_PIN_NUMBER,
          TX_PIN_NUMBER,
          RTS_PIN_NUMBER,
          CTS_PIN_NUMBER,
          UART_HWFC,
          false,
          NRF_UART_BAUDRATE_115200
      };

    APP_UART_FIFO_INIT(&comm_params,
                         UART_RX_BUF_SIZE,
                         UART_TX_BUF_SIZE,
                         uart_error_handle,
                         APP_IRQ_PRIORITY_LOWEST,
                         err_code);

    APP_ERROR_CHECK(err_code);
   /* FROM /examples/peripherical/uart END*/



    /* Configure board. */
    bsp_board_leds_init();
    startSensors();
    //gpio_init_group();
    gpio_init();


#ifndef ENABLE_LOOPBACK_TEST
    printf("\r\nStart: \r\n");
    /* Toggle LEDs. */
    int i = 0;
    while (true)
    {
         nrf_delay_ms(100);
         bsp_board_led_invert(0);
         bsp_board_led_on(1);
        // if(PIN_IN == 0)
        // {
        //  bsp_board_led_on(1);
        //}


       /* for (int i = 0; i < LEDS_NUMBER; i++)
        {
            bsp_board_led_invert(i);
            nrf_delay_ms(150);
        }*/
        
        printf(" \r\nping%d...\r\n",i);
        printSensorState();
      

        uint8_t cr;
        if (app_uart_get(&cr) == NRF_SUCCESS)
        {
           while (app_uart_put(cr) != NRF_SUCCESS);
  
            if (cr == 'q' || cr == 'Q')
            {
              bsp_board_led_invert(0);
              printf(" \r\nExit!\r\n");

              // while (true)
              {
                // Do nothing.
              }
            }
        }
        i++;
    }
#else
    // This part of the example is just for testing the loopback .
    while (true)
    {
        uart_loopback_test();
    }
#endif
}


/*
void sensorRead(void) {
  if (nrf_gpio_pin_read(GPIO_PIN_27))
   {
    nrf_gpio_pin_write(GPIO_PIN_17, S_OFF);
   } 
   else
   {
    nrf_gpio_pin_write(GPIO_PIN_17, S_ON);
   }

  if (nrf_gpio_pin_read(GPIO_PIN_28)) 
  {
    nrf_gpio_pin_write(GPIO_PIN_18, S_OFF);
  }
  else 
  {
    nrf_gpio_pin_write(GPIO_PIN_18, S_ON);
  }

  if (nrf_gpio_pin_read(GPIO_PIN_29)) 
  {
    nrf_gpio_pin_write(GPIO_PIN_19, S_OFF);
  } else 
  {
    nrf_gpio_pin_write(GPIO_PIN_19, S_ON);
  }

  if (nrf_gpio_pin_read(GPIO_PIN_30)) 
  {
    nrf_gpio_pin_write(GPIO_PIN_20, S_OFF);
  } else 
  {
    nrf_gpio_pin_write(GPIO_PIN_20, S_ON);
  }
}
*/
/**
 *@}
 **/