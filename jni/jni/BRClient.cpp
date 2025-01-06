// -- -- -- -- -- -- -- 
// by Weikton 
// -- -- -- -- -- -- --
#include "BRClient.h"
#include <stdint.h>

#include "util/CJavaWrapper.h"
// 2 = 1 (времено, пока не добавили сервера)

const char* g_szServerNames[MAX_SERVERS] = {
	"BLACK RUSSIA | RED",
	"BLACK RUSSIA | TEST"
                  // 2 = 1 !!! 194.226.49.34:7801
};

const CSetServer::CServerInstanceEncrypted g_sEncryptedAddresses[MAX_SERVERS] = {
	CSetServer::create("194.226.49.34", 1, 16, 7801, false),
	CSetServer/*Weikton*/::create("194.226.49.34", 1, 16, 7801, false)
				
};