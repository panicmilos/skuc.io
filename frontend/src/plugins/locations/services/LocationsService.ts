import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { CreateLocationRequest, Location, UpdateLocationRequest } from "../models";

export const LOCATIONS_SERVICE_ID = 'LocationsService';

export const useLocationsService = (groupId: string) => {
  
  const [locationsService, setLocationsService] = useState(new LocationsService(groupId));

  useEffect(() => {
    setLocationsService(new LocationsService(groupId))
  }, [groupId]);

  return [locationsService];
}

export class LocationsService extends CrudService<Location, CreateLocationRequest, UpdateLocationRequest> {
  constructor(groupId: string) {
    super(LOCATIONS_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/locations`)
  }

}