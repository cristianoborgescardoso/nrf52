#!/bin/bash
echo 'Erasing UICR...'
nrfjprog -f NRF52 --eraseuicr
echo 'Writing UICR Registers...'
nrfjprog  -f NRF52  --memwr 0x10001080 --val 0x03000204
nrfjprog  -f NRF52  --memwr 0x10001084 --val 0x16000000
nrfjprog  -f NRF52  --memwr 0x10001088 --val 0x1a000700
nrfjprog  -f NRF52  --memwr 0x1000108c --val 0x0d000500
echo 'Reading UICR Registers...'
nrfjprog -f NRF52 --memrd 0x10001080 --n 4
nrfjprog -f NRF52 --memrd 0x10001084 --n 4
nrfjprog -f NRF52 --memrd 0x10001088 --n 4
nrfjprog -f NRF52 --memrd 0x1000108c --n 4
nrfjprog -f NRF52 --memrd 0x10001090 --n 4
nrfjprog -f NRF52 --memrd 0x10001094 --n 4
nrfjprog -f NRF52 --memrd 0x10001098 --n 4
nrfjprog -f NRF52 --memrd 0x1000109c --n 4
nrfjprog -f NRF52 --memrd 0x100010a0 --n 4
nrfjprog -f NRF52 --memrd 0x100010a4 --n 4
nrfjprog -f NRF52 --memrd 0x100010a8 --n 4
nrfjprog -f NRF52 --memrd 0x100010ac --n 4
nrfjprog -f NRF52 --memrd 0x100010b0 --n 4
nrfjprog -f NRF52 --memrd 0x100010b4 --n 4
nrfjprog -f NRF52 --memrd 0x100010b8 --n 4
nrfjprog -f NRF52 --memrd 0x100010bc --n 4
nrfjprog -f NRF52 --memrd 0x100010c0 --n 4
nrfjprog -f NRF52 --memrd 0x100010c4 --n 4
nrfjprog -f NRF52 --memrd 0x100010c8 --n 4
nrfjprog -f NRF52 --memrd 0x100010cc --n 4
nrfjprog -f NRF52 --memrd 0x100010d0 --n 4
nrfjprog -f NRF52 --memrd 0x100010d4 --n 4
nrfjprog -f NRF52 --memrd 0x100010d8 --n 4
nrfjprog -f NRF52 --memrd 0x100010dc --n 4
nrfjprog -f NRF52 --memrd 0x100010e0 --n 4
nrfjprog -f NRF52 --memrd 0x100010e4 --n 4
nrfjprog -f NRF52 --memrd 0x100010e8 --n 4
nrfjprog -f NRF52 --memrd 0x100010ec --n 4
nrfjprog -f NRF52 --memrd 0x100010f0 --n 4
nrfjprog -f NRF52 --memrd 0x100010f4 --n 4
nrfjprog -f NRF52 --memrd 0x100010f8 --n 4
nrfjprog -f NRF52 --memrd 0x100010fc --n 4

