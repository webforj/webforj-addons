!function(e,t){"object"==typeof exports&&"undefined"!=typeof module?t(exports):"function"==typeof define&&define.amd?define(["exports"],t):t((e="undefined"!=typeof globalThis?globalThis:e||self).dwcWebAuthn={})}(this,(function(e){"use strict";var t=function(e,r){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&(e[r]=t[r])},t(e,r)};var r=function(){return r=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e},r.apply(this,arguments)};function n(e,t,r,n){return new(r||(r=Promise))((function(o,i){function a(e){try{c(n.next(e))}catch(e){i(e)}}function s(e){try{c(n.throw(e))}catch(e){i(e)}}function c(e){var t;e.done?o(e.value):(t=e.value,t instanceof r?t:new r((function(e){e(t)}))).then(a,s)}c((n=n.apply(e,t||[])).next())}))}function o(e,t){var r,n,o,i,a={label:0,sent:function(){if(1&o[0])throw o[1];return o[1]},trys:[],ops:[]};return i={next:s(0),throw:s(1),return:s(2)},"function"==typeof Symbol&&(i[Symbol.iterator]=function(){return this}),i;function s(s){return function(c){return function(s){if(r)throw new TypeError("Generator is already executing.");for(;i&&(i=0,s[0]&&(a=0)),a;)try{if(r=1,n&&(o=2&s[0]?n.return:s[0]?n.throw||((o=n.return)&&o.call(n),0):n.next)&&!(o=o.call(n,s[1])).done)return o;switch(n=0,o&&(s=[2&s[0],o.value]),s[0]){case 0:case 1:o=s;break;case 4:return a.label++,{value:s[1],done:!1};case 5:a.label++,n=s[1],s=[0];continue;case 7:s=a.ops.pop(),a.trys.pop();continue;default:if(!(o=a.trys,(o=o.length>0&&o[o.length-1])||6!==s[0]&&2!==s[0])){a=0;continue}if(3===s[0]&&(!o||s[1]>o[0]&&s[1]<o[3])){a.label=s[1];break}if(6===s[0]&&a.label<o[1]){a.label=o[1],o=s;break}if(o&&a.label<o[2]){a.label=o[2],a.ops.push(s);break}o[2]&&a.ops.pop(),a.trys.pop();continue}s=t.call(e,a)}catch(e){s=[6,e],n=0}finally{r=o=0}if(5&s[0])throw s[1];return{value:s[0]?s[1]:void 0,done:!0}}([s,c])}}}"function"==typeof SuppressedError&&SuppressedError;var i={CEREMONY_ABORTED:"CEREMONY_ABORTED",INVALID_DOMAIN:"INVALID_DOMAIN",INVALID_RP_ID:"INVALID_RP_ID",INVALID_USER_ID_LENGTH:"INVALID_USER_ID_LENGTH",MALFORMED_PUBKEYCREDPARAMS:"MALFORMED_PUBKEYCREDPARAMS",AUTHENTICATOR_GENERAL_ERROR:"AUTHENTICATOR_GENERAL_ERROR",AUTHENTICATOR_MISSING_DISCOVERABLE_CREDENTIAL_SUPPORT:"AUTHENTICATOR_MISSING_DISCOVERABLE_CREDENTIAL_SUPPORT",AUTHENTICATOR_MISSING_USER_VERIFICATION_SUPPORT:"AUTHENTICATOR_MISSING_USER_VERIFICATION_SUPPORT",AUTHENTICATOR_PREVIOUSLY_REGISTERED:"AUTHENTICATOR_PREVIOUSLY_REGISTERED",AUTHENTICATOR_NO_SUPPORTED_PUBKEYCREDPARAMS_ALG:"AUTHENTICATOR_NO_SUPPORTED_PUBKEYCREDPARAMS_ALG",PASSTHROUGH_SEE_CAUSE_PROPERTY:"PASSTHROUGH_SEE_CAUSE_PROPERTY"},a={AbortError:"AbortError",ConstraintError:"ConstraintError",InvalidStateError:"InvalidStateError",NotAllowedError:"NotAllowedError",NotSupportedError:"NotSupportedError",SecurityError:"SecurityError",TypeError:"TypeError",UnknownError:"UnknownError"},s=/^([a-z0-9]+(-[a-z0-9]+)*\.)+[a-z]{2,}$/i;function c(e){return"localhost"===e||s.test(e)}var l=function(e){function r(t){var r=t.message,n=t.code,o=t.cause,i=t.name,a=e.call(this,r,{cause:o})||this;return a.name=null!=i?i:o.name,a.code=n,a.stack=o.stack,a}return function(e,r){if("function"!=typeof r&&null!==r)throw new TypeError("Class extends value "+String(r)+" is not a constructor or null");function n(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}(r,e),r}(Error);function u(e){var t,r,n=new Uint8Array(e),o="";try{for(var i=function(e){var t="function"==typeof Symbol&&Symbol.iterator,r=t&&e[t],n=0;if(r)return r.call(e);if(e&&"number"==typeof e.length)return{next:function(){return e&&n>=e.length&&(e=void 0),{value:e&&e[n++],done:!e}}};throw new TypeError(t?"Object is not iterable.":"Symbol.iterator is not defined.")}(n),a=i.next();!a.done;a=i.next()){var s=a.value;o+=String.fromCharCode(s)}}catch(e){t={error:e}}finally{try{a&&!a.done&&(r=i.return)&&r.call(i)}finally{if(t)throw t.error}}return btoa(o).replace(/\+/g,"-").replace(/\//g,"_").replace(/=/g,"")}function d(e){for(var t=e.replace(/-/g,"+").replace(/_/g,"/"),r=(4-t.length%4)%4,n=t.padEnd(t.length+r,"="),o=atob(n),i=new ArrayBuffer(o.length),a=new Uint8Array(i),s=0;s<o.length;s++)a[s]=o.charCodeAt(s);return i}var f=function(e,t){try{e()}catch(e){console.warn("The browser extension that intercepted this WebAuthn API call incorrectly implemented ".concat(t,". You should report this error to them.\n"),e)}};function E(){return void 0!==(null===window||void 0===window?void 0:window.PublicKeyCredential)&&"function"==typeof window.PublicKeyCredential}function p(){return void 0===PublicKeyCredential.isConditionalMediationAvailable?Promise.resolve(!1):PublicKeyCredential.isConditionalMediationAvailable()}var A=new(function(){function e(){}return e.prototype.createAbortSignal=function(){if(this.controller){var e=new Error("Cancelling existing WebAuthn operation for new one");e.name="AbortError",this.controller.abort(e)}return this.controller=new AbortController,this.controller.signal},e.prototype.cancelCeremony=function(){if(this.controller){var e=new Error("Manually cancelling existing WebAuthn operation");e.name="AbortError",this.controller.abort(e),this.controller=void 0}},e}()),h=["cross-platform","platform"];function w(e){if(e&&!(h.indexOf(e)<0))return e}function b(e){var t=e.id;return r(r({},e),{id:d(t),transports:e.transports})}e.WebAuthnError=l,e.WebAuthnErrorCode=i,e.authenticate=function(e){return n(this,arguments,void 0,(function(e,t){var n,s,f,h,R,y,_,T,g,I,S;return void 0===t&&(t=!1),o(this,(function(o){switch(o.label){case 0:if(!E())throw new Error("WebAuthn is not supported in this browser");return 0!==(null===(I=e.allowCredentials)||void 0===I?void 0:I.length)&&(n=null===(S=e.allowCredentials)||void 0===S?void 0:S.map(b)),s=r(r({},e),{challenge:d(e.challenge),allowCredentials:n}),f={},t?[4,p()]:[3,2];case 1:if(!o.sent())throw Error("Browser does not support WebAuthn autofill");if(document.querySelectorAll("input[autocomplete$='webauthn']").length<1)throw Error('No <input> with "webauthn" as the only or last value in its `autocomplete` attribute was detected');f.mediation="conditional",s.allowCredentials=[],o.label=2;case 2:f.publicKey=s,f.signal=A.createAbortSignal(),o.label=3;case 3:return o.trys.push([3,5,,6]),[4,navigator.credentials.get(f)];case 4:return h=o.sent(),[3,6];case 5:throw function(e){var t=e.error,r=e.options,n=r.publicKey;if(!n)throw Error("options was missing required publicKey property");if(t.name===a.AbortError){if(r.signal instanceof AbortSignal)return new l({message:"Authentication ceremony was sent an abort signal",code:i.CEREMONY_ABORTED,cause:t})}else{if(t.name===a.NotAllowedError)return new l({message:t.message,code:i.PASSTHROUGH_SEE_CAUSE_PROPERTY,cause:t});if(t.name===a.SecurityError){var o=window.location.hostname;if(!c(o))return new l({message:"".concat(window.location.hostname," is an invalid domain"),code:i.INVALID_DOMAIN,cause:t});if(n.rpId!==o)return new l({message:'The RP ID "'.concat(n.rpId,'" is invalid for this domain'),code:i.INVALID_RP_ID,cause:t})}else if(t.name===a.UnknownError)return new l({message:"The authenticator was unable to process the specified options, or could not create a new assertion signature",code:i.AUTHENTICATOR_GENERAL_ERROR,cause:t})}return t}({error:o.sent(),options:f});case 6:if(!h)throw new Error("Authentication was not completed");return R=h.id,y=h.rawId,_=h.response,T=h.type,g=void 0,_.userHandle&&(m=_.userHandle,g=new TextDecoder("utf-8").decode(m)),[2,{id:R,rawId:u(y),response:{authenticatorData:u(_.authenticatorData),clientDataJSON:u(_.clientDataJSON),signature:u(_.signature),userHandle:g},type:T,clientExtensionResults:h.getClientExtensionResults(),authenticatorAttachment:w(h.authenticatorAttachment)}]}var m}))}))},e.base64URLStringToBuffer=d,e.browserSupportsWebAuthn=E,e.browserSupportsWebAuthnAutofill=p,e.bufferToBase64URLString=u,e.platformAuthenticatorIsAvailable=function(){return E()?PublicKeyCredential.isUserVerifyingPlatformAuthenticatorAvailable():Promise.resolve(!1)},e.register=function(e){return n(this,void 0,void 0,(function(){var t,n,s,p,h,R,y,_,T,g,I,S;return o(this,(function(o){switch(o.label){case 0:if(!E())throw new Error("WebAuthn is not supported in this browser");t=r(r({},e),{challenge:d(e.challenge),user:r(r({},e.user),{id:(m=e.user.id,(new TextEncoder).encode(m))}),excludeCredentials:null===(S=e.excludeCredentials)||void 0===S?void 0:S.map(b)}),(n={publicKey:t}).signal=A.createAbortSignal(),o.label=1;case 1:return o.trys.push([1,3,,4]),[4,navigator.credentials.create(n)];case 2:return s=o.sent(),[3,4];case 3:throw function(e){var t,r,n=e.error,o=e.options,s=o.publicKey;if(!s)throw Error("options was missing required publicKey property");if(n.name===a.AbortError){if(o.signal instanceof AbortSignal)return new l({message:"Registration ceremony was sent an abort signal",code:i.CEREMONY_ABORTED,cause:n})}else if(n.name===a.ConstraintError){if(!0===(null===(t=s.authenticatorSelection)||void 0===t?void 0:t.requireResidentKey))return new l({message:"Discoverable credentials were required but no available authenticator supported it",code:i.AUTHENTICATOR_MISSING_DISCOVERABLE_CREDENTIAL_SUPPORT,cause:n});if("required"===(null===(r=s.authenticatorSelection)||void 0===r?void 0:r.userVerification))return new l({message:"User verification was required but no available authenticator supported it",code:i.AUTHENTICATOR_MISSING_USER_VERIFICATION_SUPPORT,cause:n})}else{if(n.name===a.InvalidStateError)return new l({message:"The authenticator was previously registered",code:i.AUTHENTICATOR_PREVIOUSLY_REGISTERED,cause:n});if(n.name===a.NotAllowedError)return new l({message:n.message,code:i.PASSTHROUGH_SEE_CAUSE_PROPERTY,cause:n});if(n.name===a.NotSupportedError)return 0===s.pubKeyCredParams.filter((function(e){return"public-key"===e.type})).length?new l({message:'No entry in pubKeyCredParams was of type "public-key"',code:i.MALFORMED_PUBKEYCREDPARAMS,cause:n}):new l({message:"No available authenticator supported any of the specified pubKeyCredParams algorithms",code:i.AUTHENTICATOR_NO_SUPPORTED_PUBKEYCREDPARAMS_ALG,cause:n});if(n.name===a.SecurityError){var u=window.location.hostname;if(!c(u))return new l({message:"".concat(window.location.hostname," is an invalid domain"),code:i.INVALID_DOMAIN,cause:n});if(s.rp.id!==u)return new l({message:'The RP ID "'.concat(s.rp.id,'" is invalid for this domain'),code:i.INVALID_RP_ID,cause:n})}else if(n.name===a.TypeError){if(s.user.id.byteLength<1||s.user.id.byteLength>64)return new l({message:"User ID was not between 1 and 64 characters",code:i.INVALID_USER_ID_LENGTH,cause:n})}else if(n.name===a.UnknownError)return new l({message:"The authenticator was unable to process the specified options, or could not create a new credential",code:i.AUTHENTICATOR_GENERAL_ERROR,cause:n})}return n}({error:o.sent(),options:n});case 4:if(!s)throw new Error("Registration was not completed");return p=s.id,h=s.rawId,R=s.response,y=s.type,_=void 0,"function"==typeof R.getTransports&&(_=R.getTransports()),T=void 0,"function"==typeof R.getPublicKeyAlgorithm&&f((function(){T=R.getPublicKeyAlgorithm()}),"getPublicKeyAlgorithm()"),g=void 0,"function"==typeof R.getPublicKey&&f((function(){var e=R.getPublicKey();null!==e&&(g=u(e))}),"getPublicKey()"),"function"==typeof R.getAuthenticatorData&&f((function(){I=u(R.getAuthenticatorData())}),"getAuthenticatorData()"),[2,{id:p,rawId:u(h),response:{attestationObject:u(R.attestationObject),clientDataJSON:u(R.clientDataJSON),transports:_,publicKeyAlgorithm:T,publicKey:g,authenticatorData:I},type:y,clientExtensionResults:s.getClientExtensionResults(),authenticatorAttachment:w(s.authenticatorAttachment)}]}var m}))}))},e.webAuthnAbortService=A,Object.defineProperty(e,"__esModule",{value:!0})}));
