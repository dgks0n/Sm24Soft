/**
 * Firebug console.log .log() jQuery extension.
 *
 * @filename jquery.firebug.console.log.js
 *
 * @description
 * Normal usage of Firebug console.log method is e.g. console.log("hello world"),
 * or with passed arguments, console.log(2,4,6,8,"foo",bar). This little jQuery
 * extension checks if Firebug console.log is available. If not, the log method
 * will be ignored by browsers like IE. So the log message can stay in the source.
 *
 * @usage $.log('Message'); | $('div').find('p').log('p found');
 *
 * @see http://happygiraffe.net/blog/2007/09/26/jquery-logging/
 * @see http://stackoverflow.com/questions/5472938/does-ie9-support-console-log-and-is-it-a-real-function/
 *
 * @author Eduard Seifert <seifert.eduard@gmail.com>
 * @lastmodified 20130217
 */
jQuery.log = jQuery.fn.log = function(msg) {
    if ( typeof window.console !== 'undefined' && typeof window.console.log !== 'undefined') {
        console.log("%s: %o", msg, this);
        console.log(msg);
    }
    return this;
};