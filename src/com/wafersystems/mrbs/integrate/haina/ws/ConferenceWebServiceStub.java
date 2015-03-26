package com.wafersystems.mrbs.integrate.haina.ws;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.FaultMapKey;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.OutInAxisOperation;
import org.apache.axis2.description.WSDL2Constants;
import org.apache.axis2.wsdl.WSDLConstants;

public class ConferenceWebServiceStub extends Stub {
	protected AxisOperation[] _operations;

	// hashmaps to keep the fault mapping
	private HashMap faultExceptionNameMap = new HashMap();
	private HashMap faultExceptionClassNameMap = new HashMap();
	private HashMap faultMessageMap = new HashMap();

	private static int counter = 0;

	private static synchronized String getUniqueSuffix() {
		// reset the counter if it is greater than 99999
		if (counter > 99999) {
			counter = 0;
		}
		counter = counter + 1;
		return Long.toString(System.currentTimeMillis()) + "_" + counter;
	}

	private void populateAxisService() throws AxisFault {
		// creating the Service with a unique name
		_service = new AxisService("ConferenceWebService" + getUniqueSuffix());
		addAnonymousOperations();

		// creating the operations
		AxisOperation __operation;

		_operations = new AxisOperation[4];

		__operation = new OutInAxisOperation();

		__operation
				.setName(new QName("http://hinacom.com/", "addHospitalInfo"));
		_service.addOperation(__operation);

		_operations[0] = __operation;

		__operation = new OutInAxisOperation();

		__operation.setName(new QName("http://hinacom.com/",
				"deleteHospitalInfo"));
		_service.addOperation(__operation);

		_operations[1] = __operation;

		__operation = new OutInAxisOperation();

		__operation.setName(new QName("http://hinacom.com/",
				"setConferenceStudies"));
		_service.addOperation(__operation);

		_operations[2] = __operation;

		__operation = new OutInAxisOperation();

		__operation
				.setName(new QName("http://hinacom.com/", "getAllStudyData"));
		_service.addOperation(__operation);

		_operations[3] = __operation;

	}

	// populates the faults
	private void populateFaults() {

	}

	/**
	 * Constructor that takes in a configContext
	 */
	public ConferenceWebServiceStub(ConfigurationContext configurationContext,
			String targetEndpoint) throws AxisFault {
		this(configurationContext, targetEndpoint, false);
	}

	/**
	 * Constructor that takes in a configContext and useseperate listner
	 */
	private ConferenceWebServiceStub(ConfigurationContext configurationContext,
			String targetEndpoint, boolean useSeparateListener)
			throws AxisFault {
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient = new ServiceClient(configurationContext, _service);

		_serviceClient.getOptions()
				.setTo(new EndpointReference(targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

		// Set the soap version
		_serviceClient.getOptions().setSoapVersionURI(
				SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public ConferenceWebServiceStub(String targetEndpoint) throws AxisFault {
		this(null, targetEndpoint);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see com.hinacom.ConferenceWebService#addHospitalInfo
	 * @param addHospitalInfo0
	 */

	public ConferenceWebServiceStub.AddHospitalInfoResponse addHospitalInfo(
	ConferenceWebServiceStub.AddHospitalInfo addHospitalInfo0)
	throws java.rmi.RemoteException
	{
		MessageContext _messageContext = null;
		try {
			OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction(
					"http://hinacom.com/AddHospitalInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(_operationClient,
					WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new MessageContext();

			// create SOAP envelope with that payload
			SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), addHospitalInfo0,
					optimizeContent(new QName("http://hinacom.com/",
							"addHospitalInfo")), new QName(
							"http://hinacom.com/", "addHospitalInfo"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			MessageContext _returnMessageContext = _operationClient
					.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					ConferenceWebServiceStub.AddHospitalInfoResponse.class,
					getEnvelopeNamespaces(_returnEnv));

			return (ConferenceWebServiceStub.AddHospitalInfoResponse) object;

		} catch (AxisFault f) {

			OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(new FaultMapKey(faultElt
						.getQName(), "AddHospitalInfo"))) {
					// make the fault by reflection
					try {
						String exceptionClassName = (String) faultExceptionClassNameMap
								.get(new FaultMapKey(faultElt.getQName(),
										"AddHospitalInfo"));
						Class exceptionClass = Class
								.forName(exceptionClassName);
						Exception ex = (Exception) exceptionClass.newInstance();
						// message class
						String messageClassName = (String) faultMessageMap
								.get(new FaultMapKey(faultElt.getQName(),
										"AddHospitalInfo"));
						Class messageClass = Class.forName(messageClassName);
						Object messageObject = fromOM(faultElt, messageClass,
								null);
						Method m = exceptionClass.getMethod("setFaultMessage",
								new Class[] { messageClass });
						m.invoke(ex, new Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}
	/**
	 * Auto generated method signature
	 * 
	 * @see com.hinacom.ConferenceWebService#deleteHospitalInfo
	 * @param deleteHospitalInfo2
	 */

	public ConferenceWebServiceStub.DeleteHospitalInfoResponse deleteHospitalInfo(

	ConferenceWebServiceStub.DeleteHospitalInfo deleteHospitalInfo2)

	throws java.rmi.RemoteException

	{
		MessageContext _messageContext = null;
		try {
			OperationClient _operationClient = _serviceClient
					.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction(
					"http://hinacom.com/DeleteHospitalInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(_operationClient,
					WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new MessageContext();

			// create SOAP envelope with that payload
			SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), deleteHospitalInfo2,
					optimizeContent(new QName("http://hinacom.com/",
							"deleteHospitalInfo")), new QName(
							"http://hinacom.com/", "deleteHospitalInfo"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			MessageContext _returnMessageContext = _operationClient
					.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					ConferenceWebServiceStub.DeleteHospitalInfoResponse.class,
					getEnvelopeNamespaces(_returnEnv));

			return (ConferenceWebServiceStub.DeleteHospitalInfoResponse) object;

		} catch (AxisFault f) {

			OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(new FaultMapKey(faultElt
						.getQName(), "DeleteHospitalInfo"))) {
					// make the fault by reflection
					try {
						String exceptionClassName = (String) faultExceptionClassNameMap
								.get(new FaultMapKey(faultElt.getQName(),
										"DeleteHospitalInfo"));
						Class exceptionClass = Class
								.forName(exceptionClassName);
						Exception ex = (Exception) exceptionClass.newInstance();
						// message class
						String messageClassName = (String) faultMessageMap
								.get(new FaultMapKey(faultElt.getQName(),
										"DeleteHospitalInfo"));
						Class messageClass = Class.forName(messageClassName);
						Object messageObject = fromOM(faultElt, messageClass,
								null);
						Method m = exceptionClass.getMethod("setFaultMessage",
								new Class[] { messageClass });
						m.invoke(ex, new Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see com.hinacom.ConferenceWebService#setConferenceStudies
	 * @param studies4
	 */

	public ConferenceWebServiceStub.StudiesResponse setConferenceStudies(

	ConferenceWebServiceStub.Studies studies4)

	throws java.rmi.RemoteException

	{
		MessageContext _messageContext = null;
		try {
			OperationClient _operationClient = _serviceClient
					.createClient(_operations[2].getName());
			_operationClient.getOptions().setAction(
					"http://hinacom.com/Studies");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(_operationClient,
					WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new MessageContext();

			// create SOAP envelope with that payload
			SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), studies4, optimizeContent(new QName(
					"http://hinacom.com/", "setConferenceStudies")), new QName(
					"http://hinacom.com/", "setConferenceStudies"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			MessageContext _returnMessageContext = _operationClient
					.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					ConferenceWebServiceStub.StudiesResponse.class,
					getEnvelopeNamespaces(_returnEnv));

			return (ConferenceWebServiceStub.StudiesResponse) object;

		} catch (AxisFault f) {

			OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(new FaultMapKey(faultElt
						.getQName(), "SetConferenceStudies"))) {
					// make the fault by reflection
					try {
						String exceptionClassName = (String) faultExceptionClassNameMap
								.get(new FaultMapKey(faultElt.getQName(),
										"SetConferenceStudies"));
						Class exceptionClass = Class
								.forName(exceptionClassName);
						Exception ex = (Exception) exceptionClass.newInstance();
						// message class
						String messageClassName = (String) faultMessageMap
								.get(new FaultMapKey(faultElt.getQName(),
										"SetConferenceStudies"));
						Class messageClass = Class.forName(messageClassName);
						Object messageObject = fromOM(faultElt, messageClass,
								null);
						Method m = exceptionClass.getMethod("setFaultMessage",
								new Class[] { messageClass });
						m.invoke(ex, new Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see com.hinacom.ConferenceWebService#getAllStudyData
	 * @param getAllStudyData6
	 */

	public ConferenceWebServiceStub.GetAllStudyDataResponse getAllStudyData(

	ConferenceWebServiceStub.GetAllStudyData getAllStudyData6)

	throws java.rmi.RemoteException

	{
		MessageContext _messageContext = null;
		try {
			OperationClient _operationClient = _serviceClient
					.createClient(_operations[3].getName());
			_operationClient.getOptions().setAction(
					"http://hinacom.com/GetAllStudyData");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(_operationClient,
					WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new MessageContext();

			// create SOAP envelope with that payload
			SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), getAllStudyData6,
					optimizeContent(new QName("http://hinacom.com/",
							"getAllStudyData")), new QName(
							"http://hinacom.com/", "getAllStudyData"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			MessageContext _returnMessageContext = _operationClient
					.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					ConferenceWebServiceStub.GetAllStudyDataResponse.class,
					getEnvelopeNamespaces(_returnEnv));

			return (ConferenceWebServiceStub.GetAllStudyDataResponse) object;

		} catch (AxisFault f) {

			OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(new FaultMapKey(faultElt
						.getQName(), "GetAllStudyData"))) {
					// make the fault by reflection
					try {
						String exceptionClassName = (String) faultExceptionClassNameMap
								.get(new FaultMapKey(faultElt.getQName(),
										"GetAllStudyData"));
						Class exceptionClass = Class
								.forName(exceptionClassName);
						Exception ex = (Exception) exceptionClass.newInstance();
						// message class
						String messageClassName = (String) faultMessageMap
								.get(new FaultMapKey(faultElt.getQName(),
										"GetAllStudyData"));
						Class messageClass = Class.forName(messageClassName);
						Object messageObject = fromOM(faultElt, messageClass,
								null);
						Method m = exceptionClass.getMethod("setFaultMessage",
								new Class[] { messageClass });
						m.invoke(ex, new Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}
	/**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 */
	private Map getEnvelopeNamespaces(SOAPEnvelope env) {
		Map returnMap = new HashMap();
		Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		while (namespaceIterator.hasNext()) {
			OMNamespace ns = (OMNamespace) namespaceIterator.next();
			returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		}
		return returnMap;
	}

	private QName[] opNameArray = null;

	private boolean optimizeContent(QName opName) {

		if (opNameArray == null) {
			return false;
		}
		for (int i = 0; i < opNameArray.length; i++) {
			if (opName.equals(opNameArray[i])) {
				return true;
			}
		}
		return false;
	}

	// http://192.168.1.55/HinacomWebsvc/RIS_WEB_Svr/ConferenceWebService.asmx
	public static class AddHospitalInfoResponse implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"AddHospitalInfoResponse", "ns1");

		/**
		 * field for AddHospitalInfoResult
		 */

		protected long localAddHospitalInfoResult;

		/**
		 * Auto generated getter method
		 * 
		 * @return long
		 */
		public long getAddHospitalInfoResult() {
			return localAddHospitalInfoResult;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            AddHospitalInfoResult
		 */
		public void setAddHospitalInfoResult(long param) {

			this.localAddHospitalInfoResult = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":AddHospitalInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "AddHospitalInfoResponse", xmlWriter);
				}

			}

			namespace = "http://hinacom.com/";
			writeStartElement(null, namespace, "AddHospitalInfoResult",
					xmlWriter);

			if (localAddHospitalInfoResult == Long.MIN_VALUE) {

				throw new ADBException("AddHospitalInfoResult cannot be null!!");

			} else {
				xmlWriter.writeCharacters(ConverterUtil
						.convertToString(localAddHospitalInfoResult));
			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			elementList.add(new QName("http://hinacom.com/",
					"AddHospitalInfoResult"));

			elementList.add(ConverterUtil
					.convertToString(localAddHospitalInfoResult));

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static AddHospitalInfoResponse parse(XMLStreamReader reader)
					throws Exception {
				AddHospitalInfoResponse object = new AddHospitalInfoResponse();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"AddHospitalInfoResponse".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (AddHospitalInfoResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"AddHospitalInfoResult").equals(reader
									.getName())) {

						String content = reader.getElementText();

						object.setAddHospitalInfoResult(ConverterUtil
								.convertToLong(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new ADBException("Unexpected subelement "
								+ reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class StudyData implements ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * StudyData Namespace URI = http://hinacom.com/ Namespace Prefix = ns1
		 */

		/**
		 * field for Studies
		 */

		protected ArrayOfStudy localStudies;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStudiesTracker = false;

		public boolean isStudiesSpecified() {
			return localStudiesTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return ArrayOfStudy
		 */
		public ArrayOfStudy getStudies() {
			return localStudies;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Studies
		 */
		public void setStudies(ArrayOfStudy param) {
			localStudiesTracker = param != null;

			this.localStudies = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":StudyData", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "StudyData", xmlWriter);
				}

			}
			if (localStudiesTracker) {
				if (localStudies == null) {
					throw new ADBException("Studies cannot be null!!");
				}
				localStudies.serialize(new QName("http://hinacom.com/",
						"Studies"), xmlWriter);
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localStudiesTracker) {
				elementList.add(new QName("http://hinacom.com/", "Studies"));

				if (localStudies == null) {
					throw new ADBException("Studies cannot be null!!");
				}
				elementList.add(localStudies);
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static StudyData parse(XMLStreamReader reader)
					throws Exception {
				StudyData object = new StudyData();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"StudyData".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (StudyData) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Studies")
									.equals(reader.getName())) {

						object.setStudies(ArrayOfStudy.Factory.parse(reader));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class DeleteHospitalInfo implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"DeleteHospitalInfo", "ns1");

		/**
		 * field for Id
		 */

		protected String localId;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localIdTracker = false;

		public boolean isIdSpecified() {
			return localIdTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getId() {
			return localId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Id
		 */
		public void setId(String param) {
			localIdTracker = param != null;

			this.localId = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":DeleteHospitalInfo",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "DeleteHospitalInfo", xmlWriter);
				}

			}
			if (localIdTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "id", xmlWriter);

				if (localId == null) {
					// write the nil attribute

					throw new ADBException("id cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localId);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localIdTracker) {
				elementList.add(new QName("http://hinacom.com/", "id"));

				if (localId != null) {
					elementList.add(ConverterUtil.convertToString(localId));
				} else {
					throw new ADBException("id cannot be null!!");
				}
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static DeleteHospitalInfo parse(XMLStreamReader reader)
					throws Exception {
				DeleteHospitalInfo object = new DeleteHospitalInfo();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"DeleteHospitalInfo".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (DeleteHospitalInfo) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "id")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setId(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class ArrayOfStudy implements ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * ArrayOfStudy Namespace URI = http://hinacom.com/ Namespace Prefix =
		 * ns1
		 */

		/**
		 * field for Study This was an Array!
		 */

		protected Study[] localStudy;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStudyTracker = false;

		public boolean isStudySpecified() {
			return localStudyTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Study[]
		 */
		public Study[] getStudy() {
			return localStudy;
		}

		/**
		 * validate the array for Study
		 */
		protected void validateStudy(Study[] param) {

		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Study
		 */
		public void setStudy(Study[] param) {

			validateStudy(param);

			localStudyTracker = true;

			this.localStudy = param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            Study
		 */
		public void addStudy(Study param) {
			if (localStudy == null) {
				localStudy = new Study[] {};
			}

			// update the setting tracker
			localStudyTracker = true;

			List list = ConverterUtil.toList(localStudy);
			list.add(param);
			this.localStudy = (Study[]) list.toArray(new Study[list.size()]);

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":ArrayOfStudy",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "ArrayOfStudy", xmlWriter);
				}

			}
			if (localStudyTracker) {
				if (localStudy != null) {
					for (int i = 0; i < localStudy.length; i++) {
						if (localStudy[i] != null) {
							localStudy[i].serialize(new QName(
									"http://hinacom.com/", "Study"), xmlWriter);
						} else {

							writeStartElement(null, "http://hinacom.com/",
									"Study", xmlWriter);

							// write the nil attribute
							writeAttribute(
									"xsi",
									"http://www.w3.org/2001/XMLSchema-instance",
									"nil", "1", xmlWriter);
							xmlWriter.writeEndElement();

						}

					}
				} else {

					writeStartElement(null, "http://hinacom.com/", "Study",
							xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localStudyTracker) {
				if (localStudy != null) {
					for (int i = 0; i < localStudy.length; i++) {

						if (localStudy[i] != null) {
							elementList.add(new QName("http://hinacom.com/",
									"Study"));
							elementList.add(localStudy[i]);
						} else {

							elementList.add(new QName("http://hinacom.com/",
									"Study"));
							elementList.add(null);

						}

					}
				} else {

					elementList.add(new QName("http://hinacom.com/", "Study"));
					elementList.add(localStudy);

				}

			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ArrayOfStudy parse(XMLStreamReader reader)
					throws Exception {
				ArrayOfStudy object = new ArrayOfStudy();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"ArrayOfStudy".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (ArrayOfStudy) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					ArrayList list1 = new ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Study")
									.equals(reader.getName())) {

						// Process the array and step past its final element's
						// end.

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							list1.add(null);
							reader.next();
						} else {
							list1.add(Study.Factory.parse(reader));
						}
						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1 = false;
						while (!loopDone1) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();
							// Step out of this element
							reader.next();
							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();
							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1 = true;
							} else {
								if (new QName("http://hinacom.com/", "Study")
										.equals(reader.getName())) {

									nillableValue = reader
											.getAttributeValue(
													"http://www.w3.org/2001/XMLSchema-instance",
													"nil");
									if ("true".equals(nillableValue)
											|| "1".equals(nillableValue)) {
										list1.add(null);
										reader.next();
									} else {
										list1.add(Study.Factory.parse(reader));
									}
								} else {
									loopDone1 = true;
								}
							}
						}
						// call the converter utility to convert and set the
						// array

						object.setStudy((Study[]) ConverterUtil.convertToArray(
								Study.class, list1));

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class Studies implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"Studies", "ns1");

		/**
		 * field for Studies
		 */

		protected ArrayOfString localStudies;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStudiesTracker = false;

		public boolean isStudiesSpecified() {
			return localStudiesTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return ArrayOfString
		 */
		public ArrayOfString getStudies() {
			return localStudies;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Studies
		 */
		public void setStudies(ArrayOfString param) {
			localStudiesTracker = param != null;

			this.localStudies = param;

		}

		/**
		 * field for ConfId
		 */

		protected String localConfId;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localConfIdTracker = false;

		public boolean isConfIdSpecified() {
			return localConfIdTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getConfId() {
			return localConfId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ConfId
		 */
		public void setConfId(String param) {
			localConfIdTracker = param != null;

			this.localConfId = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":Studies", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "Studies", xmlWriter);
				}

			}
			if (localStudiesTracker) {
				if (localStudies == null) {
					throw new ADBException("studies cannot be null!!");
				}
				localStudies.serialize(new QName("http://hinacom.com/",
						"studies"), xmlWriter);
			}
			if (localConfIdTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "confId", xmlWriter);

				if (localConfId == null) {
					// write the nil attribute

					throw new ADBException("confId cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localConfId);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localStudiesTracker) {
				elementList.add(new QName("http://hinacom.com/", "studies"));

				if (localStudies == null) {
					throw new ADBException("studies cannot be null!!");
				}
				elementList.add(localStudies);
			}
			if (localConfIdTracker) {
				elementList.add(new QName("http://hinacom.com/", "confId"));

				if (localConfId != null) {
					elementList.add(ConverterUtil.convertToString(localConfId));
				} else {
					throw new ADBException("confId cannot be null!!");
				}
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Studies parse(XMLStreamReader reader)
					throws Exception {
				Studies object = new Studies();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"Studies".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (Studies) ExtensionMapper.getTypeObject(
										nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "studies")
									.equals(reader.getName())) {

						object.setStudies(ArrayOfString.Factory.parse(reader));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "confId")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setConfId(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class GetAllStudyData implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"GetAllStudyData", "ns1");

		/**
		 * field for SearchPara
		 */

		protected SearchPara localSearchPara;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localSearchParaTracker = false;

		public boolean isSearchParaSpecified() {
			return localSearchParaTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return SearchPara
		 */
		public SearchPara getSearchPara() {
			return localSearchPara;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SearchPara
		 */
		public void setSearchPara(SearchPara param) {
			localSearchParaTracker = param != null;

			this.localSearchPara = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":GetAllStudyData",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "GetAllStudyData", xmlWriter);
				}

			}
			if (localSearchParaTracker) {
				if (localSearchPara == null) {
					throw new ADBException("searchPara cannot be null!!");
				}
				localSearchPara.serialize(new QName("http://hinacom.com/",
						"searchPara"), xmlWriter);
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localSearchParaTracker) {
				elementList.add(new QName("http://hinacom.com/", "searchPara"));

				if (localSearchPara == null) {
					throw new ADBException("searchPara cannot be null!!");
				}
				elementList.add(localSearchPara);
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetAllStudyData parse(XMLStreamReader reader)
					throws Exception {
				GetAllStudyData object = new GetAllStudyData();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"GetAllStudyData".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (GetAllStudyData) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "searchPara")
									.equals(reader.getName())) {

						object.setSearchPara(SearchPara.Factory.parse(reader));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class ArrayOfString implements ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * ArrayOfString Namespace URI = http://hinacom.com/ Namespace Prefix =
		 * ns1
		 */

		/**
		 * field for String This was an Array!
		 */

		protected String[] localString;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStringTracker = false;

		public boolean isStringSpecified() {
			return localStringTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String[]
		 */
		public String[] getString() {
			return localString;
		}

		/**
		 * validate the array for String
		 */
		protected void validateString(String[] param) {

		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            String
		 */
		public void setString(String[] param) {

			validateString(param);

			localStringTracker = true;

			this.localString = param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            String
		 */
		public void addString(String param) {
			if (localString == null) {
				localString = new String[] {};
			}

			// update the setting tracker
			localStringTracker = true;

			List list = ConverterUtil.toList(localString);
			list.add(param);
			this.localString = (String[]) list.toArray(new String[list.size()]);

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":ArrayOfString",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "ArrayOfString", xmlWriter);
				}

			}
			if (localStringTracker) {
				if (localString != null) {
					namespace = "http://hinacom.com/";
					for (int i = 0; i < localString.length; i++) {

						if (localString[i] != null) {

							writeStartElement(null, namespace, "string",
									xmlWriter);

							xmlWriter.writeCharacters(ConverterUtil
									.convertToString(localString[i]));

							xmlWriter.writeEndElement();

						} else {

							// write null attribute
							namespace = "http://hinacom.com/";
							writeStartElement(null, namespace, "string",
									xmlWriter);
							writeAttribute(
									"xsi",
									"http://www.w3.org/2001/XMLSchema-instance",
									"nil", "1", xmlWriter);
							xmlWriter.writeEndElement();

						}

					}
				} else {

					// write the null attribute
					// write null attribute
					writeStartElement(null, "http://hinacom.com/", "string",
							xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}

			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localStringTracker) {
				if (localString != null) {
					for (int i = 0; i < localString.length; i++) {

						if (localString[i] != null) {
							elementList.add(new QName("http://hinacom.com/",
									"string"));
							elementList.add(ConverterUtil
									.convertToString(localString[i]));
						} else {

							elementList.add(new QName("http://hinacom.com/",
									"string"));
							elementList.add(null);

						}

					}
				} else {

					elementList.add(new QName("http://hinacom.com/", "string"));
					elementList.add(null);

				}

			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ArrayOfString parse(XMLStreamReader reader)
					throws Exception {
				ArrayOfString object = new ArrayOfString();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"ArrayOfString".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (ArrayOfString) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					ArrayList list1 = new ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "string")
									.equals(reader.getName())) {

						// Process the array and step past its final element's
						// end.

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							list1.add(null);

							reader.next();
						} else {
							list1.add(reader.getElementText());
						}
						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1 = false;
						while (!loopDone1) {
							// Ensure we are at the EndElement
							while (!reader.isEndElement()) {
								reader.next();
							}
							// Step out of this element
							reader.next();
							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();
							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1 = true;
							} else {
								if (new QName("http://hinacom.com/", "string")
										.equals(reader.getName())) {

									nillableValue = reader
											.getAttributeValue(
													"http://www.w3.org/2001/XMLSchema-instance",
													"nil");
									if ("true".equals(nillableValue)
											|| "1".equals(nillableValue)) {
										list1.add(null);

										reader.next();
									} else {
										list1.add(reader.getElementText());
									}
								} else {
									loopDone1 = true;
								}
							}
						}
						// call the converter utility to convert and set the
						// array

						object.setString((String[]) list1
								.toArray(new String[list1.size()]));

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class GetAllStudyDataResponse implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"GetAllStudyDataResponse", "ns1");

		/**
		 * field for GetAllStudyDataResult
		 */

		protected StudyData localGetAllStudyDataResult;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localGetAllStudyDataResultTracker = false;

		public boolean isGetAllStudyDataResultSpecified() {
			return localGetAllStudyDataResultTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return StudyData
		 */
		public StudyData getGetAllStudyDataResult() {
			return localGetAllStudyDataResult;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GetAllStudyDataResult
		 */
		public void setGetAllStudyDataResult(StudyData param) {
			localGetAllStudyDataResultTracker = param != null;

			this.localGetAllStudyDataResult = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":GetAllStudyDataResponse", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "GetAllStudyDataResponse", xmlWriter);
				}

			}
			if (localGetAllStudyDataResultTracker) {
				if (localGetAllStudyDataResult == null) {
					throw new ADBException(
							"GetAllStudyDataResult cannot be null!!");
				}
				localGetAllStudyDataResult.serialize(new QName(
						"http://hinacom.com/", "GetAllStudyDataResult"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localGetAllStudyDataResultTracker) {
				elementList.add(new QName("http://hinacom.com/",
						"GetAllStudyDataResult"));

				if (localGetAllStudyDataResult == null) {
					throw new ADBException(
							"GetAllStudyDataResult cannot be null!!");
				}
				elementList.add(localGetAllStudyDataResult);
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetAllStudyDataResponse parse(XMLStreamReader reader)
					throws Exception {
				GetAllStudyDataResponse object = new GetAllStudyDataResponse();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"GetAllStudyDataResponse".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (GetAllStudyDataResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"GetAllStudyDataResult").equals(reader
									.getName())) {

						object.setGetAllStudyDataResult(StudyData.Factory
								.parse(reader));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class StudiesResponse implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"StudiesResponse", "ns1");

		/**
		 * field for StudiesResult
		 */

		protected int localStudiesResult;

		/**
		 * Auto generated getter method
		 * 
		 * @return int
		 */
		public int getStudiesResult() {
			return localStudiesResult;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StudiesResult
		 */
		public void setStudiesResult(int param) {

			this.localStudiesResult = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":StudiesResponse",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "StudiesResponse", xmlWriter);
				}

			}

			namespace = "http://hinacom.com/";
			writeStartElement(null, namespace, "StudiesResult", xmlWriter);

			if (localStudiesResult == Integer.MIN_VALUE) {

				throw new ADBException("StudiesResult cannot be null!!");

			} else {
				xmlWriter.writeCharacters(ConverterUtil
						.convertToString(localStudiesResult));
			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			elementList.add(new QName("http://hinacom.com/", "StudiesResult"));

			elementList.add(ConverterUtil.convertToString(localStudiesResult));

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static StudiesResponse parse(XMLStreamReader reader)
					throws Exception {
				StudiesResponse object = new StudiesResponse();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"StudiesResponse".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (StudiesResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "StudiesResult")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setStudiesResult(ConverterUtil
								.convertToInt(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new ADBException("Unexpected subelement "
								+ reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class Study implements ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * Study Namespace URI = http://hinacom.com/ Namespace Prefix = ns1
		 */

		/**
		 * field for Patientid
		 */

		protected String localPatientid;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localPatientidTracker = false;

		public boolean isPatientidSpecified() {
			return localPatientidTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getPatientid() {
			return localPatientid;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Patientid
		 */
		public void setPatientid(String param) {
			localPatientidTracker = param != null;

			this.localPatientid = param;

		}

		/**
		 * field for Sex
		 */

		protected int localSex;

		/**
		 * Auto generated getter method
		 * 
		 * @return int
		 */
		public int getSex() {
			return localSex;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Sex
		 */
		public void setSex(int param) {

			this.localSex = param;

		}

		/**
		 * field for Birthdate
		 */

		protected String localBirthdate;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localBirthdateTracker = false;

		public boolean isBirthdateSpecified() {
			return localBirthdateTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getBirthdate() {
			return localBirthdate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Birthdate
		 */
		public void setBirthdate(String param) {
			localBirthdateTracker = param != null;

			this.localBirthdate = param;

		}

		/**
		 * field for Datetime
		 */

		protected String localDatetime;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localDatetimeTracker = false;

		public boolean isDatetimeSpecified() {
			return localDatetimeTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getDatetime() {
			return localDatetime;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Datetime
		 */
		public void setDatetime(String param) {
			localDatetimeTracker = param != null;
			try {
				String temp = param.substring(0, 8);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date date = format.parse(temp);
				format = new SimpleDateFormat("yyyy-MM-dd");
				temp = format.format(date);
				this.localDatetime = temp;
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		/**
		 * field for Modality
		 */

		protected String localModality;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localModalityTracker = false;

		public boolean isModalitySpecified() {
			return localModalityTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getModality() {
			return localModality;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Modality
		 */
		public void setModality(String param) {
			localModalityTracker = param != null;

			this.localModality = param;

		}

		/**
		 * field for Studyuid
		 */

		protected String localStudyuid;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStudyuidTracker = false;

		public boolean isStudyuidSpecified() {
			return localStudyuidTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getStudyuid() {
			return localStudyuid;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Studyuid
		 */
		public void setStudyuid(String param) {
			localStudyuidTracker = param != null;

			this.localStudyuid = param;

		}

		/**
		 * field for InstitutaionName
		 */

		protected String localInstitutaionName;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localInstitutaionNameTracker = false;

		public boolean isInstitutaionNameSpecified() {
			return localInstitutaionNameTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getInstitutaionName() {
			return localInstitutaionName;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            InstitutaionName
		 */
		public void setInstitutaionName(String param) {
			localInstitutaionNameTracker = param != null;

			this.localInstitutaionName = param;

		}

		/**
		 * field for Name
		 */

		protected String localName;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localNameTracker = false;

		public boolean isNameSpecified() {
			return localNameTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getName() {
			return localName;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Name
		 */
		public void setName(String param) {
			localNameTracker = param != null;

			this.localName = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":Study", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "Study", xmlWriter);
				}

			}
			if (localPatientidTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Patientid", xmlWriter);

				if (localPatientid == null) {
					// write the nil attribute

					throw new ADBException("Patientid cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localPatientid);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://hinacom.com/";
			writeStartElement(null, namespace, "Sex", xmlWriter);

			if (localSex == Integer.MIN_VALUE) {

				throw new ADBException("Sex cannot be null!!");

			} else {
				xmlWriter.writeCharacters(ConverterUtil
						.convertToString(localSex));
			}

			xmlWriter.writeEndElement();
			if (localBirthdateTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Birthdate", xmlWriter);

				if (localBirthdate == null) {
					// write the nil attribute

					throw new ADBException("Birthdate cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localBirthdate);

				}

				xmlWriter.writeEndElement();
			}
			if (localDatetimeTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Datetime", xmlWriter);

				if (localDatetime == null) {
					// write the nil attribute

					throw new ADBException("Datetime cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localDatetime);

				}

				xmlWriter.writeEndElement();
			}
			if (localModalityTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Modality", xmlWriter);

				if (localModality == null) {
					// write the nil attribute

					throw new ADBException("Modality cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localModality);

				}

				xmlWriter.writeEndElement();
			}
			if (localStudyuidTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Studyuid", xmlWriter);

				if (localStudyuid == null) {
					// write the nil attribute

					throw new ADBException("Studyuid cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localStudyuid);

				}

				xmlWriter.writeEndElement();
			}
			if (localInstitutaionNameTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "InstitutaionName",
						xmlWriter);

				if (localInstitutaionName == null) {
					// write the nil attribute

					throw new ADBException("InstitutaionName cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localInstitutaionName);

				}

				xmlWriter.writeEndElement();
			}
			if (localNameTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Name", xmlWriter);

				if (localName == null) {
					// write the nil attribute

					throw new ADBException("Name cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localName);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localPatientidTracker) {
				elementList.add(new QName("http://hinacom.com/", "Patientid"));

				if (localPatientid != null) {
					elementList.add(ConverterUtil
							.convertToString(localPatientid));
				} else {
					throw new ADBException("Patientid cannot be null!!");
				}
			}
			elementList.add(new QName("http://hinacom.com/", "Sex"));

			elementList.add(ConverterUtil.convertToString(localSex));
			if (localBirthdateTracker) {
				elementList.add(new QName("http://hinacom.com/", "Birthdate"));

				if (localBirthdate != null) {
					elementList.add(ConverterUtil
							.convertToString(localBirthdate));
				} else {
					throw new ADBException("Birthdate cannot be null!!");
				}
			}
			if (localDatetimeTracker) {
				elementList.add(new QName("http://hinacom.com/", "Datetime"));

				if (localDatetime != null) {
					elementList.add(ConverterUtil
							.convertToString(localDatetime));
				} else {
					throw new ADBException("Datetime cannot be null!!");
				}
			}
			if (localModalityTracker) {
				elementList.add(new QName("http://hinacom.com/", "Modality"));

				if (localModality != null) {
					elementList.add(ConverterUtil
							.convertToString(localModality));
				} else {
					throw new ADBException("Modality cannot be null!!");
				}
			}
			if (localStudyuidTracker) {
				elementList.add(new QName("http://hinacom.com/", "Studyuid"));

				if (localStudyuid != null) {
					elementList.add(ConverterUtil
							.convertToString(localStudyuid));
				} else {
					throw new ADBException("Studyuid cannot be null!!");
				}
			}
			if (localInstitutaionNameTracker) {
				elementList.add(new QName("http://hinacom.com/",
						"InstitutaionName"));

				if (localInstitutaionName != null) {
					elementList.add(ConverterUtil
							.convertToString(localInstitutaionName));
				} else {
					throw new ADBException("InstitutaionName cannot be null!!");
				}
			}
			if (localNameTracker) {
				elementList.add(new QName("http://hinacom.com/", "Name"));

				if (localName != null) {
					elementList.add(ConverterUtil.convertToString(localName));
				} else {
					throw new ADBException("Name cannot be null!!");
				}
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Study parse(XMLStreamReader reader) throws Exception {
				Study object = new Study();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"Study".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (Study) ExtensionMapper.getTypeObject(
										nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Patientid")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setPatientid(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Sex")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setSex(ConverterUtil.convertToInt(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new ADBException("Unexpected subelement "
								+ reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Birthdate")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setBirthdate(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Datetime")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setDatetime(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Modality")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setModality(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Studyuid")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setStudyuid(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"InstitutaionName")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setInstitutaionName(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Name")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setName(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class ExtensionMapper {

		public static Object getTypeObject(String namespaceURI,
				String typeName, XMLStreamReader reader) throws Exception {

			if ("http://hinacom.com/".equals(namespaceURI)
					&& "Study".equals(typeName)) {

				return Study.Factory.parse(reader);

			}

			if ("http://hinacom.com/".equals(namespaceURI)
					&& "StudyData".equals(typeName)) {

				return StudyData.Factory.parse(reader);

			}

			if ("http://hinacom.com/".equals(namespaceURI)
					&& "ArrayOfStudy".equals(typeName)) {

				return ArrayOfStudy.Factory.parse(reader);

			}

			if ("http://hinacom.com/".equals(namespaceURI)
					&& "SearchPara".equals(typeName)) {

				return SearchPara.Factory.parse(reader);

			}

			if ("http://hinacom.com/".equals(namespaceURI)
					&& "ArrayOfString".equals(typeName)) {

				return ArrayOfString.Factory.parse(reader);

			}

			throw new ADBException("Unsupported type " + namespaceURI + " "
					+ typeName);
		}

	}

	public static class SearchPara implements ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * SearchPara Namespace URI = http://hinacom.com/ Namespace Prefix = ns1
		 */

		/**
		 * field for Importdateend
		 */

		protected String localImportdateend;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localImportdateendTracker = false;

		public boolean isImportdateendSpecified() {
			return localImportdateendTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getImportdateend() {
			return localImportdateend;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Importdateend
		 */
		public void setImportdateend(String param) {
			localImportdateendTracker = param != null;

			this.localImportdateend = param;

		}

		/**
		 * field for Studydatestart
		 */

		protected String localStudydatestart;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStudydatestartTracker = false;

		public boolean isStudydatestartSpecified() {
			return localStudydatestartTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getStudydatestart() {
			return localStudydatestart;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Studydatestart
		 */
		public void setStudydatestart(String param) {
			localStudydatestartTracker = param != null;

			this.localStudydatestart = param;

		}

		/**
		 * field for HospitalId
		 */

		protected String localHospitalId;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localHospitalIdTracker = false;

		public boolean isHospitalIdSpecified() {
			return localHospitalIdTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getHospitalId() {
			return localHospitalId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            HospitalId
		 */
		public void setHospitalId(String param) {
			localHospitalIdTracker = param != null;

			this.localHospitalId = param;

		}

		/**
		 * field for Name
		 */

		protected String localName;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localNameTracker = false;

		public boolean isNameSpecified() {
			return localNameTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getName() {
			return localName;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Name
		 */
		public void setName(String param) {
			localNameTracker = param != null;

			this.localName = param;

		}

		/**
		 * field for Patientid
		 */

		protected String localPatientid;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localPatientidTracker = false;

		public boolean isPatientidSpecified() {
			return localPatientidTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getPatientid() {
			return localPatientid;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Patientid
		 */
		public void setPatientid(String param) {
			localPatientidTracker = param != null;

			this.localPatientid = param;

		}

		/**
		 * field for Sex
		 */

		protected String localSex;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localSexTracker = false;

		public boolean isSexSpecified() {
			return localSexTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getSex() {
			return localSex;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Sex
		 */
		public void setSex(String param) {
			localSexTracker = param != null;

			this.localSex = param;

		}

		/**
		 * field for Birthdatestart
		 */

		protected String localBirthdatestart;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localBirthdatestartTracker = false;

		public boolean isBirthdatestartSpecified() {
			return localBirthdatestartTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getBirthdatestart() {
			return localBirthdatestart;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Birthdatestart
		 */
		public void setBirthdatestart(String param) {
			localBirthdatestartTracker = param != null;

			this.localBirthdatestart = param;

		}

		/**
		 * field for Birthdateend
		 */

		protected String localBirthdateend;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localBirthdateendTracker = false;

		public boolean isBirthdateendSpecified() {
			return localBirthdateendTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getBirthdateend() {
			return localBirthdateend;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Birthdateend
		 */
		public void setBirthdateend(String param) {
			localBirthdateendTracker = param != null;

			this.localBirthdateend = param;

		}

		/**
		 * field for Studydateend
		 */

		protected String localStudydateend;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localStudydateendTracker = false;

		public boolean isStudydateendSpecified() {
			return localStudydateendTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getStudydateend() {
			return localStudydateend;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Studydateend
		 */
		public void setStudydateend(String param) {
			localStudydateendTracker = param != null;

			this.localStudydateend = param;

		}

		/**
		 * field for Importdatestart
		 */

		protected String localImportdatestart;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localImportdatestartTracker = false;

		public boolean isImportdatestartSpecified() {
			return localImportdatestartTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getImportdatestart() {
			return localImportdatestart;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Importdatestart
		 */
		public void setImportdatestart(String param) {
			localImportdatestartTracker = param != null;

			this.localImportdatestart = param;

		}

		/**
		 * field for Modality
		 */

		protected String localModality;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localModalityTracker = false;

		public boolean isModalitySpecified() {
			return localModalityTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getModality() {
			return localModality;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Modality
		 */
		public void setModality(String param) {
			localModalityTracker = param != null;

			this.localModality = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":SearchPara", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "SearchPara", xmlWriter);
				}

			}
			if (localImportdateendTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Importdateend", xmlWriter);

				if (localImportdateend == null) {
					// write the nil attribute

					throw new ADBException("Importdateend cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localImportdateend);

				}

				xmlWriter.writeEndElement();
			}
			if (localStudydatestartTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Studydatestart", xmlWriter);

				if (localStudydatestart == null) {
					// write the nil attribute

					throw new ADBException("Studydatestart cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localStudydatestart);

				}

				xmlWriter.writeEndElement();
			}
			if (localHospitalIdTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "HospitalId", xmlWriter);

				if (localHospitalId == null) {
					// write the nil attribute

					throw new ADBException("HospitalId cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localHospitalId);

				}

				xmlWriter.writeEndElement();
			}
			if (localNameTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Name", xmlWriter);

				if (localName == null) {
					// write the nil attribute

					throw new ADBException("Name cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localName);

				}

				xmlWriter.writeEndElement();
			}
			if (localPatientidTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Patientid", xmlWriter);

				if (localPatientid == null) {
					// write the nil attribute

					throw new ADBException("Patientid cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localPatientid);

				}

				xmlWriter.writeEndElement();
			}
			if (localSexTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Sex", xmlWriter);

				if (localSex == null) {
					// write the nil attribute

					throw new ADBException("Sex cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localSex);

				}

				xmlWriter.writeEndElement();
			}
			if (localBirthdatestartTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Birthdatestart", xmlWriter);

				if (localBirthdatestart == null) {
					// write the nil attribute

					throw new ADBException("Birthdatestart cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localBirthdatestart);

				}

				xmlWriter.writeEndElement();
			}
			if (localBirthdateendTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Birthdateend", xmlWriter);

				if (localBirthdateend == null) {
					// write the nil attribute

					throw new ADBException("Birthdateend cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localBirthdateend);

				}

				xmlWriter.writeEndElement();
			}
			if (localStudydateendTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Studydateend", xmlWriter);

				if (localStudydateend == null) {
					// write the nil attribute

					throw new ADBException("Studydateend cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localStudydateend);

				}

				xmlWriter.writeEndElement();
			}
			if (localImportdatestartTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Importdatestart", xmlWriter);

				if (localImportdatestart == null) {
					// write the nil attribute

					throw new ADBException("Importdatestart cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localImportdatestart);

				}

				xmlWriter.writeEndElement();
			}
			if (localModalityTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "Modality", xmlWriter);

				if (localModality == null) {
					// write the nil attribute

					throw new ADBException("Modality cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localModality);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localImportdateendTracker) {
				elementList.add(new QName("http://hinacom.com/",
						"Importdateend"));

				if (localImportdateend != null) {
					elementList.add(ConverterUtil
							.convertToString(localImportdateend));
				} else {
					throw new ADBException("Importdateend cannot be null!!");
				}
			}
			if (localStudydatestartTracker) {
				elementList.add(new QName("http://hinacom.com/",
						"Studydatestart"));

				if (localStudydatestart != null) {
					elementList.add(ConverterUtil
							.convertToString(localStudydatestart));
				} else {
					throw new ADBException("Studydatestart cannot be null!!");
				}
			}
			if (localHospitalIdTracker) {
				elementList.add(new QName("http://hinacom.com/", "HospitalId"));

				if (localHospitalId != null) {
					elementList.add(ConverterUtil
							.convertToString(localHospitalId));
				} else {
					throw new ADBException("HospitalId cannot be null!!");
				}
			}
			if (localNameTracker) {
				elementList.add(new QName("http://hinacom.com/", "Name"));

				if (localName != null) {
					elementList.add(ConverterUtil.convertToString(localName));
				} else {
					throw new ADBException("Name cannot be null!!");
				}
			}
			if (localPatientidTracker) {
				elementList.add(new QName("http://hinacom.com/", "Patientid"));

				if (localPatientid != null) {
					elementList.add(ConverterUtil
							.convertToString(localPatientid));
				} else {
					throw new ADBException("Patientid cannot be null!!");
				}
			}
			if (localSexTracker) {
				elementList.add(new QName("http://hinacom.com/", "Sex"));

				if (localSex != null) {
					elementList.add(ConverterUtil.convertToString(localSex));
				} else {
					throw new ADBException("Sex cannot be null!!");
				}
			}
			if (localBirthdatestartTracker) {
				elementList.add(new QName("http://hinacom.com/",
						"Birthdatestart"));

				if (localBirthdatestart != null) {
					elementList.add(ConverterUtil
							.convertToString(localBirthdatestart));
				} else {
					throw new ADBException("Birthdatestart cannot be null!!");
				}
			}
			if (localBirthdateendTracker) {
				elementList
						.add(new QName("http://hinacom.com/", "Birthdateend"));

				if (localBirthdateend != null) {
					elementList.add(ConverterUtil
							.convertToString(localBirthdateend));
				} else {
					throw new ADBException("Birthdateend cannot be null!!");
				}
			}
			if (localStudydateendTracker) {
				elementList
						.add(new QName("http://hinacom.com/", "Studydateend"));

				if (localStudydateend != null) {
					elementList.add(ConverterUtil
							.convertToString(localStudydateend));
				} else {
					throw new ADBException("Studydateend cannot be null!!");
				}
			}
			if (localImportdatestartTracker) {
				elementList.add(new QName("http://hinacom.com/",
						"Importdatestart"));

				if (localImportdatestart != null) {
					elementList.add(ConverterUtil
							.convertToString(localImportdatestart));
				} else {
					throw new ADBException("Importdatestart cannot be null!!");
				}
			}
			if (localModalityTracker) {
				elementList.add(new QName("http://hinacom.com/", "Modality"));

				if (localModality != null) {
					elementList.add(ConverterUtil
							.convertToString(localModality));
				} else {
					throw new ADBException("Modality cannot be null!!");
				}
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static SearchPara parse(XMLStreamReader reader)
					throws Exception {
				SearchPara object = new SearchPara();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"SearchPara".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (SearchPara) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Importdateend")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setImportdateend(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"Studydatestart").equals(reader.getName())) {

						String content = reader.getElementText();

						object.setStudydatestart(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "HospitalId")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setHospitalId(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Name")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setName(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Patientid")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setPatientid(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Sex")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setSex(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"Birthdatestart").equals(reader.getName())) {

						String content = reader.getElementText();

						object.setBirthdatestart(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Birthdateend")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setBirthdateend(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Studydateend")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setStudydateend(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"Importdatestart").equals(reader.getName())) {

						String content = reader.getElementText();

						object.setImportdatestart(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "Modality")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setModality(ConverterUtil
								.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class AddHospitalInfo implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"AddHospitalInfo", "ns1");

		/**
		 * field for Id
		 */

		protected String localId;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localIdTracker = false;

		public boolean isIdSpecified() {
			return localIdTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getId() {
			return localId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Id
		 */
		public void setId(String param) {
			localIdTracker = param != null;

			this.localId = param;

		}

		/**
		 * field for Name
		 */

		protected String localName;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localNameTracker = false;

		public boolean isNameSpecified() {
			return localNameTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getName() {
			return localName;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Name
		 */
		public void setName(String param) {
			localNameTracker = param != null;

			this.localName = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":AddHospitalInfo",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "AddHospitalInfo", xmlWriter);
				}

			}
			if (localIdTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "id", xmlWriter);

				if (localId == null) {
					// write the nil attribute

					throw new ADBException("id cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localId);

				}

				xmlWriter.writeEndElement();
			}
			if (localNameTracker) {
				namespace = "http://hinacom.com/";
				writeStartElement(null, namespace, "name", xmlWriter);

				if (localName == null) {
					// write the nil attribute

					throw new ADBException("name cannot be null!!");

				} else {

					xmlWriter.writeCharacters(localName);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			if (localIdTracker) {
				elementList.add(new QName("http://hinacom.com/", "id"));

				if (localId != null) {
					elementList.add(ConverterUtil.convertToString(localId));
				} else {
					throw new ADBException("id cannot be null!!");
				}
			}
			if (localNameTracker) {
				elementList.add(new QName("http://hinacom.com/", "name"));

				if (localName != null) {
					elementList.add(ConverterUtil.convertToString(localName));
				} else {
					throw new ADBException("name cannot be null!!");
				}
			}

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static AddHospitalInfo parse(XMLStreamReader reader)
					throws Exception {
				AddHospitalInfo object = new AddHospitalInfo();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"AddHospitalInfo".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (AddHospitalInfo) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "id")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setId(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/", "name")
									.equals(reader.getName())) {

						String content = reader.getElementText();

						object.setName(ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class DeleteHospitalInfoResponse implements ADBBean {

		public static final QName MY_QNAME = new QName("http://hinacom.com/",
				"DeleteHospitalInfoResponse", "ns1");

		/**
		 * field for DeleteHospitalInfoResult
		 */

		protected long localDeleteHospitalInfoResult;

		/**
		 * Auto generated getter method
		 * 
		 * @return long
		 */
		public long getDeleteHospitalInfoResult() {
			return localDeleteHospitalInfoResult;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DeleteHospitalInfoResult
		 */
		public void setDeleteHospitalInfoResult(long param) {

			this.localDeleteHospitalInfoResult = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return OMElement
		 */
		public OMElement getOMElement(final QName parentQName,
				final OMFactory factory) throws ADBException {

			OMDataSource dataSource = new ADBDataSource(this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final QName parentQName, XMLStreamWriter xmlWriter)
				throws XMLStreamException, ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final QName parentQName,
				XMLStreamWriter xmlWriter, boolean serializeType)
				throws XMLStreamException, ADBException {

			String prefix = null;
			String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				String namespacePrefix = registerPrefix(xmlWriter,
						"http://hinacom.com/");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":DeleteHospitalInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "DeleteHospitalInfoResponse", xmlWriter);
				}

			}

			namespace = "http://hinacom.com/";
			writeStartElement(null, namespace, "DeleteHospitalInfoResult",
					xmlWriter);

			if (localDeleteHospitalInfoResult == Long.MIN_VALUE) {

				throw new ADBException(
						"DeleteHospitalInfoResult cannot be null!!");

			} else {
				xmlWriter.writeCharacters(ConverterUtil
						.convertToString(localDeleteHospitalInfoResult));
			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://hinacom.com/")) {
				return "ns1";
			}
			return BeanUtil.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace,
				String localPart, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace,
				String attName, String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName,
				String attValue, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName,
				QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(QName qname, XMLStreamWriter xmlWriter)
				throws XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":"
							+ ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(ConverterUtil
							.convertToString(qname));
				}

			} else {
				xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter)
				throws XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite.append(ConverterUtil
									.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(ConverterUtil
								.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(XMLStreamWriter xmlWriter,
				String namespace) throws XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					if (uri == null || uri.length() == 0) {
						break;
					}
					prefix = BeanUtil.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public XMLStreamReader getPullParser(QName qName) throws ADBException {

			ArrayList elementList = new ArrayList();
			ArrayList attribList = new ArrayList();

			elementList.add(new QName("http://hinacom.com/",
					"DeleteHospitalInfoResult"));

			elementList.add(ConverterUtil
					.convertToString(localDeleteHospitalInfoResult));

			return new ADBXMLStreamReaderImpl(qName, elementList.toArray(),
					attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static DeleteHospitalInfoResponse parse(
					XMLStreamReader reader) throws Exception {
				DeleteHospitalInfoResponse object = new DeleteHospitalInfoResponse();

				int event;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						String fullTypeName = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"type");
						if (fullTypeName != null) {
							String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							String type = fullTypeName.substring(fullTypeName
									.indexOf(":") + 1);

							if (!"DeleteHospitalInfoResponse".equals(type)) {
								// find namespace for the prefix
								String nsUri = reader.getNamespaceContext()
										.getNamespaceURI(nsPrefix);
								return (DeleteHospitalInfoResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					Vector handledAttributes = new Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new QName("http://hinacom.com/",
									"DeleteHospitalInfoResult").equals(reader
									.getName())) {

						String content = reader.getElementText();

						object.setDeleteHospitalInfoResult(ConverterUtil
								.convertToLong(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new ADBException("Unexpected subelement "
								+ reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new ADBException("Unexpected subelement "
								+ reader.getName());

				} catch (XMLStreamException e) {
					throw new Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	private OMElement toOM(ConferenceWebServiceStub.AddHospitalInfo param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.AddHospitalInfo.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(
			ConferenceWebServiceStub.AddHospitalInfoResponse param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.AddHospitalInfoResponse.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(ConferenceWebServiceStub.DeleteHospitalInfo param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.DeleteHospitalInfo.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(
			ConferenceWebServiceStub.DeleteHospitalInfoResponse param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param
					.getOMElement(
							ConferenceWebServiceStub.DeleteHospitalInfoResponse.MY_QNAME,
							OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(ConferenceWebServiceStub.Studies param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.Studies.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(ConferenceWebServiceStub.StudiesResponse param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.StudiesResponse.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(ConferenceWebServiceStub.GetAllStudyData param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.GetAllStudyData.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private OMElement toOM(
			ConferenceWebServiceStub.GetAllStudyDataResponse param,
			boolean optimizeContent) throws AxisFault {

		try {
			return param.getOMElement(
					ConferenceWebServiceStub.GetAllStudyDataResponse.MY_QNAME,
					OMAbstractFactory.getOMFactory());
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	private SOAPEnvelope toEnvelope(SOAPFactory factory,
			ConferenceWebServiceStub.AddHospitalInfo param,
			boolean optimizeContent, QName methodQName) throws AxisFault {

		try {

			SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							ConferenceWebServiceStub.AddHospitalInfo.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private SOAPEnvelope toEnvelope(SOAPFactory factory,
			ConferenceWebServiceStub.DeleteHospitalInfo param,
			boolean optimizeContent, QName methodQName) throws AxisFault {

		try {

			SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									ConferenceWebServiceStub.DeleteHospitalInfo.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private SOAPEnvelope toEnvelope(SOAPFactory factory,
			ConferenceWebServiceStub.Studies param, boolean optimizeContent,
			QName methodQName) throws AxisFault {

		try {

			SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody()
					.addChild(
							param.getOMElement(
									ConferenceWebServiceStub.Studies.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private SOAPEnvelope toEnvelope(SOAPFactory factory,
			ConferenceWebServiceStub.GetAllStudyData param,
			boolean optimizeContent, QName methodQName) throws AxisFault {

		try {

			SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							ConferenceWebServiceStub.GetAllStudyData.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (ADBException e) {
			throw AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	/**
	 * get the default envelope
	 */
	private SOAPEnvelope toEnvelope(SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private Object fromOM(OMElement param, Class type, Map extraNamespaces)
			throws AxisFault {

		try {

			if (ConferenceWebServiceStub.AddHospitalInfo.class.equals(type)) {

				return ConferenceWebServiceStub.AddHospitalInfo.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.AddHospitalInfoResponse.class
					.equals(type)) {

				return ConferenceWebServiceStub.AddHospitalInfoResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.DeleteHospitalInfo.class.equals(type)) {

				return ConferenceWebServiceStub.DeleteHospitalInfo.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.DeleteHospitalInfoResponse.class
					.equals(type)) {

				return ConferenceWebServiceStub.DeleteHospitalInfoResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.Studies.class.equals(type)) {

				return ConferenceWebServiceStub.Studies.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.StudiesResponse.class.equals(type)) {

				return ConferenceWebServiceStub.StudiesResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.GetAllStudyData.class.equals(type)) {

				return ConferenceWebServiceStub.GetAllStudyData.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (ConferenceWebServiceStub.GetAllStudyDataResponse.class
					.equals(type)) {

				return ConferenceWebServiceStub.GetAllStudyDataResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

		} catch (Exception e) {
			throw AxisFault.makeFault(e);
		}
		return null;
	}

}
