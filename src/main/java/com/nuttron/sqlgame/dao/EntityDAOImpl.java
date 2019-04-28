package com.nuttron.sqlgame.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.stereotype.Repository;

import com.nuttron.sqlgame.util.Expression;
import com.nuttron.sqlgame.util.FilterNode;
import com.nuttron.sqlgame.util.Operator;

@Repository
public class EntityDAOImpl implements EntityDAO {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<?> getEntity(FilterNode filterNode) throws Exception {
		List<?> entityList = null;
		ArrayList<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = builder.createQuery( filterNode.getEntityClass() );
		criteriaQuery.distinct( true );
		try {
			Root<Class<?>> pRoot = criteriaQuery.from( filterNode.getEntityClass() );
			criteriaQuery.select( pRoot );
			filter( criteriaQuery, builder, pRoot, null, filterNode, null, predicateList );
			if ( !predicateList.isEmpty() ) {
				Predicate[] predArray = new Predicate[predicateList.size()];
				predicateList.toArray( predArray );
				criteriaQuery.where( predArray );
			}

			TypedQuery<Class<?>> createQuery = em.createQuery( criteriaQuery );
			entityList = createQuery.getResultList();
		}
		catch (EntityExistsException ee) {
			throw new EntityExistsException( ee.getMessage() );
		}
		catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException( iae.getMessage() );
		}
		catch (TransactionRequiredException tre) {
			throw new TransactionRequiredException( tre.getMessage() );
		}
		catch (Exception e) {
			throw new Exception( e.getMessage() );
		}
		return entityList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Fetch filter(CriteriaQuery criteriaQuery, CriteriaBuilder builder, Root parent, Fetch child,
			FilterNode parentNode, FilterNode childNode, List predicateList) throws Exception {
		try {
			Fetch tempChild = null;
			FilterNode currentNode = null;
			if ( childNode == null && parentNode != null ) {
				currentNode = parentNode;
			}
			else {
				currentNode = childNode;
			}
			for ( Expression expression : currentNode.getExpressions() ) {
				SingularAttribute propertyName = expression.getPropertyName();
				Operator operator = expression.getOperator();
				Object valueObject = expression.getValueObject();
				Predicate equal = null;
				switch ( operator ) {
					case EQUAL:
						if ( child == null ) {
							if ( valueObject instanceof Long ) {
								equal = builder.equal( parent.get( propertyName ), valueObject );
							}
							else if ( valueObject instanceof String ) {
								equal = builder.equal( parent.get( propertyName ), valueObject );
							}
							else if ( valueObject instanceof Short ) {
								equal = builder.equal( parent.get( propertyName ), valueObject );
							}
							else if ( valueObject instanceof Object ) {
								Field[] declaredFields = valueObject.getClass().getDeclaredFields();
								for ( Field f : declaredFields ) {
									f.setAccessible( true );
									Object value = f.get( valueObject );
									if ( value != null ) {
										equal = builder.equal( parent.get( propertyName ).get( f.getName() ), value );
									}
								}
							}
						}
						else {
							equal = builder.equal( ( (Join<?, ?>) child ).get( propertyName ), valueObject );
						}
						predicateList.add( equal );
						break;
					case NOT_EQUAL:
					case CONTAINS:
					case NOT_CONTAINS:
					case IN:
					case NOT_IN:
					case BEGINS_WITH:
					case ENDS_WITH:
					default:
				}
			}
			for ( SetAttribute setAttribute : currentNode.getChildFields() ) {
				if ( setAttribute == null ) {
					continue;
				}
				if ( child == null ) {
					child = parent.fetch( setAttribute );
				}
				else if ( parent == null ) {
					tempChild = child.fetch( setAttribute );
				}
				else if ( child != null && parent != null ) {
					child = parent.fetch( setAttribute );
				}
				FilterNode node = currentNode.getChildNodeMap().get( setAttribute );
				if ( tempChild != null ) {
					filter( criteriaQuery, builder, null, tempChild, currentNode, node, predicateList );
				}
				else {
					filter( criteriaQuery, builder, null, child, currentNode, node, predicateList );
				}
			}
			for ( SingularAttribute singularAttribute : currentNode.getSiblingFields() ) {
				if ( singularAttribute == null ) {
					continue;
				}
				if ( child == null ) {
					child = parent.fetch( singularAttribute );
				}
				else if ( parent == null ) {
					tempChild = child.fetch( singularAttribute );
				}
				else if ( child != null && parent != null ) {
					child = parent.fetch( singularAttribute );
				}
				FilterNode node = currentNode.getChildNodeMap().get( singularAttribute );
				if ( tempChild != null ) {
					filter( criteriaQuery, builder, null, tempChild, currentNode, node, predicateList );
				}
				else {
					filter( criteriaQuery, builder, null, child, currentNode, node, predicateList );
				}
			}
			return child;
		}
		catch (EntityExistsException ee) {
			throw new EntityExistsException( ee.getMessage() );
		}
		catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException( iae.getMessage() );
		}
		catch (TransactionRequiredException tre) {
			throw new TransactionRequiredException( tre.getMessage() );
		}
		catch (Exception e) {
			throw new Exception( e.getMessage() );
		}
	}

	@SuppressWarnings("rawtypes")
	public FilterNode getFilterNodeFromEntity(Object entityObject, Object parentEntityObject, FilterNode filterNode) throws Exception {
		try {
			if ( filterNode == null ) {
				filterNode = new FilterNode( entityObject.getClass(), em );
			}
			Metamodel metamodel = em.getMetamodel();
			EntityType entity = metamodel.entity( entityObject.getClass() );
			Field[] declaredFields = entityObject.getClass().getDeclaredFields();
			for ( Field f : declaredFields ) {
				f.setAccessible( true );
				Object value = f.get( entityObject );
				if ( value == null || ( value != null && value.equals( parentEntityObject ) ) || f.getType().getName().equalsIgnoreCase( "long" ) ) {
					continue;
				}
				else if ( getDataTypes().contains( f.getType().getName() ) ) {
					filterNode.addCondition( f.getName(), Operator.EQUAL, value );
				}
				else if ( value instanceof Collection<?> ) {
					Collection collectionObject = (Collection) f.get( entityObject );
					Iterator iterator = collectionObject.iterator();
					while ( iterator.hasNext() ) {
						Object obj = iterator.next();
						if ( obj != null ) {
							FilterNode childNode = new FilterNode( obj.getClass(), em );
							filterNode.addChild( (SetAttribute) entity.getDeclaredSet( f.getName() ), childNode );
							getFilterNodeFromEntity( obj, entityObject, childNode );
						}
					}

				}
				else if ( value instanceof Object ) {
					FilterNode siblingNode = new FilterNode( value.getClass(), em );
					filterNode.addSibling( (SingularAttribute) entity.getSingularAttribute( f.getName() ), siblingNode );
					getFilterNodeFromEntity( value, entityObject, siblingNode );
				}
			}
		}
		catch (Exception e) {
			throw new Exception( "Exception while creating Filternode from the entity object" );
		}
		return filterNode;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getEntity(Object entityObject) throws Exception {
		List<?> entityList = null;
		try {
			FilterNode filterNode = getFilterNodeFromEntity( entityObject, null, null );
			entityList = getEntity( filterNode );
		}
		catch (Exception e) {
			throw new Exception( "Exception while getting entity object" );
		}
		return entityList;
	}

	private ArrayList<String> getDataTypes() {
		ArrayList<String> datatypes = new ArrayList<String>();
		datatypes.add( "java.lang.String" );
		datatypes.add( "java.lang.Long" );
		datatypes.add( "java.util.Date" );
		datatypes.add( "java.lang.Double" );
		datatypes.add( "java.lang.Short" );
		datatypes.add( "java.lang.Integer" );
		datatypes.add( "java.lang.Float" );
		return datatypes;
	}

	public List<Object[]> executeAndGetResult(String query) {
		Query q = em.createNativeQuery( query );
		List<Object[]> result = q.getResultList();
		return result;
	}
}
